package com.helloztt.im.service.entity

import com.helloztt.im.service.annotations.AllArgsConstructor
import com.helloztt.im.service.annotations.NoArgsConstructor
import com.helloztt.im.service.enums.IMSupplier
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 13:51
 */
@Entity
@Table(name = "im_user")
@NoArgsConstructor
@AllArgsConstructor
data class IMUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column var id: Long?,
        @Column var merId: String?,
        @Column var supplier: IMSupplier?,
        @Column var userName: String?,
        @Column var password: String?,
        @Column var nickName: String?,
        @Column var created: LocalDateTime?
)