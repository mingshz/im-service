package com.helloztt.im.service.service.impl

import com.helloztt.im.service.annotations.KTBean
import com.helloztt.im.service.entity.IMAgent
import com.helloztt.im.service.enums.IMSupplier
import com.helloztt.im.service.model.IMPublicAccount
import com.helloztt.im.service.repository.IMAgentRepository
import com.helloztt.im.service.service.IMAgentService
import com.helloztt.im.service.service.factory.EaseMobIMFactory
import com.helloztt.im.service.service.factory.IMFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

/**
 *
 * @author helloztt
 * @since 2018-05-18 17:21
 */
@Service
@KTBean
class IMAgentServiceImpl(
        @Autowired private val imAgentRepository: IMAgentRepository,
        @Autowired private val imPublicAccount: IMPublicAccount
) : IMAgentService {

    private lateinit var imFactory: IMFactory

    @PostConstruct
    fun init() {
        when (imPublicAccount.imSupplier) {
            IMSupplier.EASEMOB -> imFactory = EaseMobIMFactory(imPublicAccount)
        }
    }

    override fun findAll(): List<IMAgent> {
        return imAgentRepository.findAll()
    }

    @Transactional
    override fun editAgent(agent: IMAgent): IMAgent {
        var oldAgent: IMAgent? = null
        if (agent.id != null) {
            oldAgent = imAgentRepository.findByIdAndSupplier(agent.id as Long, imPublicAccount.imSupplier)
        }
        if (oldAgent == null) {
            oldAgent = agent
            oldAgent.id = null
            oldAgent.supplier = imPublicAccount.imSupplier
        } else {
            oldAgent.agentId = agent.agentId
            oldAgent.nickName = agent.nickName
            oldAgent.configId = agent.configId
        }
        oldAgent.uri = imFactory.getWebIMUrl(oldAgent)
        imAgentRepository.save(oldAgent)
        return oldAgent
    }

    override fun findByAgentId(id: Long): IMAgent? {
        return imAgentRepository.findOne(id)
    }

    override fun removeAgent(id: Long) {
        imAgentRepository.delete(id)
    }

    override fun online(id: Long, isOnline: Boolean) {
        imAgentRepository.updateIsOnline(id, isOnline)
    }

    override fun enable(id: Long, enabled: Boolean) {
        imAgentRepository.updateEnabled(id, enabled)
    }
}