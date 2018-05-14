package com.helloztt.im.service.service

import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.enums.IMSupplier

/**
 * TODO
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 15:54
 */
interface IMUserService {
    /**
     * 新增im用户
     */
    fun addIMUser(user: IMUser)

    /**
     * 查找im用户
     */
    fun findByUserName(userName: String, supplier: IMSupplier): IMUser?

    fun findAll(): List<IMUser>
}