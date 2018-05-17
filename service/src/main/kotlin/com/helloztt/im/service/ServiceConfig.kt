package com.helloztt.im.service

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 10:40
 */
@Configuration
@ComponentScan(value = ["com.helloztt.im.service.service","com.helloztt.im.service.entity"])
@EnableJpaRepositories(basePackages = ["com.helloztt.im.service.repository"])
@EnableAspectJAutoProxy
open class ServiceConfig