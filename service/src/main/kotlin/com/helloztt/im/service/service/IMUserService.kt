package com.helloztt.im.service.service

import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.enums.IMSupplier

/**
 * @author helloztt
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
}