package com.helloztt.im.service.service

import com.helloztt.im.service.ServiceConfigTest
import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.enums.IMSupplier
import org.junit.Test

import org.junit.Assert.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

/**
 * TODO
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 15:58
 */
class IMUserServiceTest : ServiceConfigTest() {
    @Autowired
    private val imUserService: IMUserService? = null

    @Test
    fun addIMUser() {
        val userName = UUID.randomUUID().toString()
        val supplier = IMSupplier.EASEMOB
        val imUser = IMUser(null
                , UUID.randomUUID().toString()
                , supplier
                , userName
                , UUID.randomUUID().toString()
                , UUID.randomUUID().toString()
                , null
        )
        imUserService?.addIMUser(imUser)
        println(imUserService?.findAll())
        val actualIMUser = imUserService?.findByUserName(userName, supplier)
        println(actualIMUser)
    }

}