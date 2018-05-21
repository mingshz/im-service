package com.helloztt.im.service.entity

import com.helloztt.im.service.annotations.KTBean
import com.helloztt.im.service.enums.IMSupplier
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author helloztt
 * @since 2018-05-14 13:51
 */
@Entity
@Table(name = "im_user")
@KTBean
data class IMUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column var id: Long? = null,
        @Column var merId: String,
        /**
         * 客服供应商
         */
        @Column var supplier: IMSupplier? = null,
        /**
         * im用户名
         */
        @Column(length = 50) var userName: String,
        /**
         * im密码
         */
        @Column(length = 50) var password: String,
        @Column(length = 50) var nickName: String? = null,
        @Column var created: LocalDateTime? = null
)