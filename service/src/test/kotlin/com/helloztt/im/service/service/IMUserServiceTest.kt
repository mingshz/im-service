package com.helloztt.im.service.service

import com.helloztt.im.service.IMServiceConfigTest
import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.enums.IMSupplier
import com.helloztt.im.service.merId
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 15:58
 */
class IMUserServiceTest : IMServiceConfigTest() {
    @Autowired
    private lateinit var imUserService: IMUserService

    @Test
    fun addIMUser() {
        val userName = "17145947881"
        val supplier = IMSupplier.EASEMOB
        val imUser = IMUser(merId = merId
                , userName = userName
                , password = userName)
        imUserService.addIMUser(imUser)
        val actualIMUser = imUserService.findByUserName(userName, supplier)
        println(actualIMUser)

        //再注册一遍
        imUserService.addIMUser(imUser)
        imUserService.findByUserName(userName, supplier)
    }

}