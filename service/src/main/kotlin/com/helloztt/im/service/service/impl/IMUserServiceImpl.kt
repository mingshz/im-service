package com.helloztt.im.service.service.impl

import com.helloztt.im.service.entity.IMUser
import com.helloztt.im.service.entity.IMUser_
import com.helloztt.im.service.enums.IMSupplier
import com.helloztt.im.service.repository.IMUserRepository
import com.helloztt.im.service.service.IMUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

/**
 *
 * @author helloztt
 * @version V1.2.5
 * @since 2018-05-14 15:55
 */
@Service
class IMUserServiceImpl : IMUserService {

    @Autowired
    private val imUserRepository: IMUserRepository? = null
    @Autowired
    private val entityManager: EntityManager? = null

    override fun addIMUser(user: IMUser) {
        user.created = LocalDateTime.now()
        imUserRepository?.save(user)
    }

    override fun findByUserName(userName: String, supplier: IMSupplier): IMUser? {
        if (StringUtils.isEmpty(userName)) {
            throw Exception("参数错误")
        }

        val cb: CriteriaBuilder = entityManager!!.criteriaBuilder
        val cq: CriteriaQuery<IMUser> = cb.createQuery(IMUser::class.java)
        val root: Root<IMUser> = cq.from(IMUser::class.java)
        cq.where(cb.and(
                cb.equal(root.get(IMUser_.userName), userName),
                cb.equal(root.get(IMUser_.supplier), supplier)
        ))
        return entityManager.createQuery(cq).singleResult
    }

    override fun findAll(): List<IMUser> {
        val cb: CriteriaBuilder = entityManager!!.criteriaBuilder
        val cq: CriteriaQuery<IMUser> = cb.createQuery(IMUser::class.java)
        return entityManager.createQuery(cq).resultList
    }
}