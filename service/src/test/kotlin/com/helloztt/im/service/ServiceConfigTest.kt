package com.helloztt.im.service

import com.helloztt.im.service.annotations.NoArgsConstructor
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.context.annotation.AdviceMode
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.ImportResource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.annotation.Resource

/**
 * TODO
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 10:59
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@EnableAspectJAutoProxy
@ImportResource(value = "classpath:/datasource_local.xml")
class DatabaseConfigTest


@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = [ServiceConfig::class, DatabaseConfigTest::class])
@NoArgsConstructor
open class ServiceConfigTest