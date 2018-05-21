package com.helloztt.im.service.service.factory

import com.helloztt.im.service.entity.IMAgent
import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.model.IMPublicAccount

/**
 * IM工厂
 *
 * @author helloztt
 * @since 2018-05-17 15:54
 */
interface IMFactory {
    val imPublicAccount: IMPublicAccount
    /**
     * 新增IM用户
     */
    fun addUser(imUser: IMUser)

    /**
     * 获取web渠道客服聊天地址
     */
    fun getWebIMUrl(imAgent: IMAgent) : String
}