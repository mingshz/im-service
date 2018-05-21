package com.helloztt.im.service.repository

import com.helloztt.im.service.entity.IMAgent
import com.helloztt.im.service.enums.IMSupplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

/**
 *
 * @author helloztt
 * @since 2018-05-18 17:02
 */
interface IMAgentRepository : JpaRepository<IMAgent, Long>, JpaSpecificationExecutor<IMAgent> {
    /**
     * 根据id和客服供应商查找客服
     *
     * @param id 客服id
     * @param supplier 客服供应商
     */
    fun findByIdAndSupplier(id: Long, supplier: IMSupplier): IMAgent?

    /**
     * 更新客服在线状态
     */
    @Transactional
    @Modifying
    @Query(value = "update IMAgent agent set agent.isOnline = ?2 where agent.id = ?1")
    fun updateIsOnline(id: Long, isOnline: Boolean)

    /**
     * 更新客服是否可用
     */
    @Transactional
    @Modifying
    @Query(value = "update IMAgent agent set agent.enabled = ?2 where agent.id = ?1")
    fun updateEnabled(id: Long, enabled: Boolean)
}