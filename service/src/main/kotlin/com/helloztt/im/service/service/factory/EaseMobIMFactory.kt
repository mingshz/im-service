package com.helloztt.im.service.service.factory

import com.fasterxml.jackson.databind.JsonNode
import com.helloztt.im.service.entity.IMAgent
import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.enums.IMSupplier
import com.helloztt.im.service.exceptions.AuthenticateException
import com.helloztt.im.service.exceptions.RequestLimitException
import com.helloztt.im.service.model.IMPublicAccount
import com.helloztt.im.service.utils.JsonUtils
import com.helloztt.im.service.utils.Symbol
import org.apache.commons.logging.LogFactory
import org.apache.http.HttpHeaders
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.HttpClientBuilder
import java.io.IOException
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

/**
 * 环信IM
 *
 * @author helloztt
 * @since 2018-05-17 15:57
 */
class EaseMobIMFactory(account: IMPublicAccount) : IMFactory {

    private val log = LogFactory.getLog(EaseMobIMFactory::class.java)

    override var imPublicAccount: IMPublicAccount = account

    private val requestConfig: RequestConfig = RequestConfig.custom()
            .setConnectTimeout(30000)
            .setConnectionRequestTimeout(5000)
            .setSocketTimeout(30000)
            .build()
    private val httpClientBuilder: HttpClientBuilder = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(requestConfig)
            .setDefaultCookieStore(BasicCookieStore())

    private val urlPrefix = "https://a1.easemob.com"
    //虽然是错误信息，但我们认为它是合法的
    private val makeAsSuccess: Set<String> = setOf(
            "用户名已存在"
    )
    private var token: String? = null

    init {
        if (imPublicAccount.imSupplier != IMSupplier.EASEMOB) {
            log.info("Ease Mob is not running")
        } else {
            if (imPublicAccount.imURL == null) {
                imPublicAccount.imURL = urlPrefix
            }
            imPublicAccount.imURL = "${imPublicAccount.imURL}/${imPublicAccount.organName()}/${imPublicAccount.appName()}"
        }
    }

    override fun addUser(imUser: IMUser) {
        val token = getAppToken()
        val addUserPost = HttpPost("${imPublicAccount.imURL}/users")
        addUserPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.mimeType)
        addUserPost.setHeader("Authorization", "Bearer $token")

        val data = HashMap<String, String>(3)
        data["username"] = imUser.userName
        data["password"] = imUser.password
        if (imUser.nickName != null) {
            data["nickname"] = imUser.nickName as String
        }

        val entity = EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON)
                .setBinary(JsonUtils.writeValueToByte(data))
                .build()
        addUserPost.entity = entity
        try {
            val resultJsonNode = execute(addUserPost)
            val channels = resultJsonNode.get("entities")
            imUser.created = LocalDateTime.ofInstant(Instant.ofEpochMilli(channels[0].get("created").asLong()),
                    TimeZone.getDefault().toZoneId())
        } catch (e: IllegalArgumentException) {
            //把用户名已存在也作为成功处理
            if (makeAsSuccess.contains(e.message)) {
                imUser.created = LocalDateTime.now()
            } else {
                throw e
            }
        }
    }

    override fun getWebIMUrl(imAgent: IMAgent): String {
        return "https://kefu.easemob.com/webim/im.html?configId=${imAgent.configId}&agentName=${imAgent.agentId}"
    }

    /**
     * 根据环信账号获取token
     */
    private fun getAppToken(): String {
        if (token != null) {
            return token as String
        }
        var appToken: String? = null
        val tokenPost = HttpPost("${imPublicAccount.imURL}/token")
        tokenPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.mimeType)

        val data = HashMap<String, String>(3)
        data["grant_type"] = "client_credentials"
        data["client_id"] = imPublicAccount.appID
        data["client_secret"] = imPublicAccount.appSecret

        val entity = EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON)
                .setBinary(JsonUtils.writeValueToByte(data))
                .build()
        tokenPost.entity = entity
        httpClientBuilder.build().use { client ->
            client.execute(tokenPost).use { response ->
                val code = response.statusLine.statusCode
                if (code == 200) {
                    val responseBody = JsonUtils.mapper.readTree(response.entity.content)
                    appToken = responseBody.get("access_token").asText()
                }
            }
        }
        if (appToken == null) {
            throw AuthenticateException("授权失败")
        }
        return appToken as String
    }

    private fun execute(request: HttpUriRequest): JsonNode {
        val successCode = 200
        var tryNum = 0
        var code = 0
        while (tryNum <= 3 && code != successCode) {
            httpClientBuilder.build().use { client ->
                client.execute(request).use { response ->
                    code = response.statusLine.statusCode
                    val responseBody = JsonUtils.mapper.readTree(response.entity.content)
                    log.debug("请求参数:${JsonUtils.writeValueToString(request)},返回结果$responseBody")
                    when (code) {
                        200 -> return responseBody
                    //在注册用户时返回400表示 用户已存在、用户名或密码为空、用户名不合法[见用户名规则]
                        400 -> {
                            val error = responseBody.get("error").asText()
                            log.error("请求环信接口异常:$error")
                            //用户名已存在
                            when (error) {
                                "duplicate_unique_property_exists" -> throw IllegalArgumentException("用户名已存在")
                                "organization_application_not_found" -> throw IllegalArgumentException("应用不存在")
                                else -> {
                                    log.error("add user error:$error")
                                    throw IllegalArgumentException("参数错误")
                                }
                            }
                        }
                    //未授权[无token、token错误、token过期]
                        401 -> {
                            if (tryNum < 3) {
                                log.error(responseBody.get("error").asText())
                                log.error(responseBody.get("error_description").asText())
                                token = null
                            } else {
                                log.error("add user token error")
                                throw AuthenticateException("认证失败")
                            }
                        }
                        404 -> throw IOException("unexpected code:$code")
                    /*同一个 APP 每秒最多可调用30次，超过的部分会返回429或503错误。
                     所以在调用程序中，如果碰到了这样的错误，需要稍微暂停一下并且重试。
                     如果该限流控制不满足需求，请联系商务经理开放更高的权限。*/
                        429, 503 -> {
                            if (tryNum < 3) {
                                try {
                                    Thread.sleep(500)
                                } catch (e: InterruptedException) {
                                }
                            } else {
                                throw RequestLimitException("超过调用频率限制")
                            }
                        }
                        else -> {
                            log.error("error:$responseBody")
                            throw IOException("unexpected code:$code")
                        }
                    }
                }
            }
            tryNum++
        }
        throw Exception("环信接口请求失败")
    }
}

/**
 * 环信appKey格式为 ${orgName}#${appName}
 */
private fun IMPublicAccount.organName(): String? {
    return this.appKey.split(Symbol.WELL_NUMBER.value)[0]
}

private fun IMPublicAccount.appName(): String? {
    return this.appKey.split(Symbol.WELL_NUMBER.value)[1]
}