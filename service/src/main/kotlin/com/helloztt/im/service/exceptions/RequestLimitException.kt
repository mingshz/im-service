package com.helloztt.im.service.exceptions

/**
 * 请求接口超过调用频率限制，即接口被限流
 *
 * @author helloztt
 * @since 2018-05-17 11:05
 */
class RequestLimitException : Exception {
    constructor()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)

    constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}