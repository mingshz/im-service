package com.helloztt.im.service.exceptions

/**
 * 认证失败
 * @author helloztt
 * @since 2018-05-17 11:02
 */
class AuthenticateException : Exception {
    constructor()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)

    constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}