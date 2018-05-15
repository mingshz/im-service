package com.helloztt.im.service.service.work

import com.helloztt.im.service.enums.IMSupplier
import com.helloztt.im.service.event.AddIMUserEvent
import com.helloztt.im.service.model.IMPublicAccount
import com.helloztt.im.service.repository.IMUserRepository
import com.helloztt.im.service.utils.JsonUtils
import com.helloztt.im.service.utils.Symbol
import org.apache.commons.logging.LogFactory
import org.apache.http.HttpHeaders
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

/**
 *
 * @author helloztt
 * @since 2018-05-14 16:45
 */
@Service(value = "easeMobIMWorker")
class EaseMobIMWorker {
    internal var log = LogFactory.getLog(EaseMobIMWorker::class.java)

    @Autowired
    private val userRepository: IMUserRepository? = null
    @Autowired
    private val imPublicAccount: IMPublicAccount? = null

    private val requestConfig: RequestConfig = RequestConfig.custom()
            .setConnectTimeout(30000)
            .setConnectionRequestTimeout(5000)
            .setSocketTimeout(30000)
            .build()
    private val httpClientBuilder: HttpClientBuilder = HttpClientBuilder
            .create()
            .setDefaultCookieStore(BasicCookieStore())
    private val URL_PREFIX = "https://a1.easemob.com"

    @PostConstruct
    fun init() {
        imPublicAccount!!
        if (imPublicAccount.imSupplier != IMSupplier.EASEMOB) {
            log.info("Ease Mob is not running")
            return
        }
        if (imPublicAccount.imURL == null) {
            imPublicAccount.imURL = URL_PREFIX
        }
        httpClientBuilder.setDefaultRequestConfig(requestConfig)
    }

    @EventListener
    fun addUser(event: AddIMUserEvent) {

    }

    /**
     * 根据环信账号获取token
     */
    private fun getAppToken(): String {
        var appToken: String? = null
        val token = HttpPost("${imPublicAccount!!.imURL}/${imPublicAccount.organName()}/${imPublicAccount.appName()}/token")
        token.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.mimeType)

        val data = HashMap<String, String>()
        data["grant_type"] = "client_credentials"
        data["client_id"] = imPublicAccount.appID
        data["client_secret"] = imPublicAccount.appSecret

        val entity = EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON)

                .setBinary(JsonUtils().writeValueToByte(data))
                .build()
        token.entity = entity
        httpClientBuilder.build().use { client ->
            client.execute(token).use { response ->
                val code = response.statusLine.statusCode
                if (code == 200) {
                    TODO("JsonUtils()??")
                    val responseBody = JsonUtils().mapper.readTree(response.entity.content)
                    appToken = responseBody.get("access_token").asText()
                }
            }
        }
        if(appToken == null){
            TODO("整理异常")
            throw Exception("授权失败")
        }
        return appToken as String
    }
}

/**
 * 环信appKey格式为 ${orgName}#${appName}
 */
fun IMPublicAccount.organName(): String? {
    return this.appKey?.split(Symbol.WELL_NUMBER.value)[0]
}

fun IMPublicAccount.appName(): String? {
    return this.appKey?.split(Symbol.WELL_NUMBER.value)[1]
}