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
        @Column var supplier: IMSupplier? = null,
        @Column var userName: String,
        @Column var password: String,
        @Column var nickName: String? = null,
        @Column var created: LocalDateTime? = null
)