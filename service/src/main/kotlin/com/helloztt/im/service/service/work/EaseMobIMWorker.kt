package com.helloztt.im.service.service.work

import com.fasterxml.jackson.databind.ObjectMapper
import com.helloztt.im.service.repository.IMUserRepository
import org.apache.commons.logging.LogFactory
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.HashMap

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

    private var httpClientBuilder: HttpClientBuilder? = null
    private var requestConfig: RequestConfig? = null
    private val objectMapper = ObjectMapper()
    private val merchantToken = HashMap<Int, String>()
}