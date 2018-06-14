package com.helloztt.im.service.entity

import com.helloztt.im.service.annotations.KTBean
import com.helloztt.im.service.enums.IMSupplier
import javax.persistence.*

/**
 * 客服
 *
 * @author helloztt
 * @since 2018-05-18 09:28
 */
@Entity
@Table(name = "im_agent")
@KTBean
data class IMAgent(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column var id: Long? = null,
        /**
         * 客服供应商
         */
        @Column var supplier: IMSupplier? = null,
        /**
         * 客服
         */
        @Column(length = 100) var agentId: String,
        /**
         * 客服昵称
         */
        @Column(length = 100) var nickName: String,
        /**
         * 聊天页面样式配置
         */
        @Column(length = 200) var configId: String?,
        /**
         * 上下线
         */
        @Column var isOnline: Boolean = false,
        /**
         * 客服聊天地址
         */
        @Column(length = 500) var uri: String? = null,
        /**
         * 客服是否可用
         */
        @Column(columnDefinition="timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP") var enabled: Boolean = true

)