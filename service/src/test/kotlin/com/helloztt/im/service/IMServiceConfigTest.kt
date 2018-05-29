package com.helloztt.im.service

import com.helloztt.im.service.annotations.KTBean
import com.helloztt.im.service.enums.IMSupplier
import com.helloztt.im.service.model.IMPublicAccount
import com.helloztt.im.service.utils.ProfileConstant
import me.jiangcai.lib.test.SpringWebTest
import org.junit.runner.RunWith
import org.springframework.context.annotation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.LocalDateTime
import java.util.*

/**
 * TODO
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 10:59
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@EnableAspectJAutoProxy
@ImportResource(value = ["classpath:/datasource_local.xml"])
class DatabaseConfigTest

val merId = UUID.randomUUID().toString()

@Configuration
@KTBean
abstract class IMUserAccountConfig {
    @Bean
    fun imPublicAccount(): IMPublicAccount {
        return IMPublicAccount(
                mchID = merId,
                imSupplier = IMSupplier.EASEMOB,
                appKey = "helloztt#sc-test",
                appID = "YXA6Zr9_wFmXEei4fFtYAQPvsg",
                appSecret = "YXA6yimVkYd0gGKlddTsfWkZajrcD0s",
                imURL = null,
                expireTime = LocalDateTime.now().plusYears(1)
        )
    }
}

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = [IMUserAccountConfig::class, IMServiceConfig::class, DatabaseConfigTest::class])
@ActiveProfiles(ProfileConstant.unitTest)
abstract class IMServiceConfigTest:SpringWebTest()