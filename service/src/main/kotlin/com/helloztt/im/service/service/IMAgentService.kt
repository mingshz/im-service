package com.helloztt.im.service.service

import com.helloztt.im.service.entity.IMAgent

/**
 *
 * @author helloztt
 * @since 2018-05-18 17:07
 */
interface IMAgentService {

    /**
     * 客服列表
     */
    fun findAll():List<IMAgent>
    /**
     * 新增/删除客服
     * @param agent 客服信息，包括agentId,nickName,
     */
    fun editAgent(agent: IMAgent): IMAgent

    /**
     * 根据主键查找客服
     */
    fun findByAgentId(id: Long) : IMAgent?

    /**
     * 删除客服
     * @param id 主键id
     */
    fun removeAgent(id: Long)

    /**
     * 客服上/下线
     * @param id 主键id
     * @param isOnline true:上线;false:下线
     */
    fun online(id: Long, isOnline: Boolean)

    /**
     * 启用/禁用客服
     * @param id 主键id
     * @param enabled true:启用;false:禁用
     */
    fun enable(id: Long, enabled: Boolean)
}