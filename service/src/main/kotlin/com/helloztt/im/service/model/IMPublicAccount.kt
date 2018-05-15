package com.helloztt.im.service.model

import com.helloztt.im.service.enums.IMSupplier
import java.time.LocalDateTime

/**
 *
 * @author helloztt
 * @since 2018-05-14 10:49
 */
abstract class IMPublicAccount(
        /**
         * 商户ID
         */
        var mchID: String,
        /**
         * 客服供应商
         */
        var imSupplier: IMSupplier,
        /**
         * 客服应用
         */
        var appKey: String,
        var appID: String,
        var appSecret: String,
        var imURL: String,
        var expireTime: LocalDateTime

)