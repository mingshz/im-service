package com.helloztt.im.service.repository

import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.enums.IMSupplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/**
 * TODO
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 15:53
 */
interface IMUserRepository : JpaRepository<IMUser, Long>, JpaSpecificationExecutor<IMUser> {
    /**
     * 根据用户名和供应商查找im用户
     */
    fun findByUserNameAndSupplier(userName: String, supplier: IMSupplier): IMUser?
}