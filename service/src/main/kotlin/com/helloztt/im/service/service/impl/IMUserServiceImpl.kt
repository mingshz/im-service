package com.helloztt.im.service.service.impl

import com.helloztt.im.service.annotations.KTBean
import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.enums.IMSupplier
import com.helloztt.im.service.exceptions.AuthenticateException
import com.helloztt.im.service.exceptions.RequestLimitException
import com.helloztt.im.service.model.IMPublicAccount
import com.helloztt.im.service.repository.IMUserRepository
import com.helloztt.im.service.service.IMUserService
import com.helloztt.im.service.service.factory.EaseMobIMFactory
import com.helloztt.im.service.service.factory.IMFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.io.IOException
import java.lang.reflect.UndeclaredThrowableException
import java.time.LocalDateTime
import javax.annotation.PostConstruct

/**
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 15:55
 */
@Service
@KTBean
class IMUserServiceImpl : IMUserService {
    @Autowired
    private lateinit var imUserRepository: IMUserRepository
    @Autowired
    private lateinit var imPublicAccount: IMPublicAccount

    private lateinit var imFactory: IMFactory

    @PostConstruct
    fun init() {
        when (imPublicAccount.imSupplier) {
            IMSupplier.EASEMOB -> imFactory = EaseMobIMFactory(imPublicAccount)
        }
    }

    @Transactional
    override fun addIMUser(user: IMUser) {
        if (findByUserName(user.userName, imPublicAccount.imSupplier) != null) {
            return
        }
        user.supplier = imPublicAccount.imSupplier
        user.created = LocalDateTime.now()
        if (user.nickName == null) {
            user.nickName = user.userName
        }
        try {
            imFactory.addUser(imUser = user)
            imUserRepository.save(user)
        } catch (ex: UndeclaredThrowableException) {
            doWithException(ex)
        }
    }

    override fun findByUserName(userName: String, supplier: IMSupplier): IMUser? {
        if (StringUtils.isEmpty(userName)) {
            throw IllegalArgumentException()
        }

        return imUserRepository.findByUserNameAndSupplier(userName, supplier)
    }

    @Throws(IOException::class, IllegalArgumentException::class, AuthenticateException::class, RequestLimitException::class)
    private fun doWithException(ex: UndeclaredThrowableException) {
        val exception = ex.undeclaredThrowable
        if (exception is IOException
                || exception is IllegalArgumentException
                || exception is AuthenticateException
                || exception is RequestLimitException)
            throw exception
        throw ex
    }
}