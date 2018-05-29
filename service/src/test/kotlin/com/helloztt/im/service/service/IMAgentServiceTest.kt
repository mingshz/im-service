package com.helloztt.im.service.service

import com.helloztt.im.service.IMServiceConfigTest
import com.helloztt.im.service.entity.IMAgent
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

/**
 *
 * @author helloztt
 * @since 2018-05-21 09:13
 */
class IMAgentServiceTest : IMServiceConfigTest() {
    @Autowired
    private lateinit var imAgentService: IMAgentService
    private lateinit var mockIMAgent: IMAgent

    @Before
    fun mockData() {
        mockIMAgent = mockAgent()
        imAgentService.editAgent(mockIMAgent)
        assertNotNull(mockIMAgent.id)
    }

    @Test
    fun editAgent() {
        val imAgentId = mockIMAgent.id as Long
        //校验新增
        val imAgent = imAgentService.findByAgentId(imAgentId)
        assertNotNull(imAgent)

        //修改
        val agentId = "agentIdChange${UUID.randomUUID()}"
        val nickName = "nickNameChange${UUID.randomUUID()}"
        val configId = "configIdChange${UUID.randomUUID()}"

        imAgent?.agentId = agentId
        imAgent?.nickName = nickName
        imAgent?.configId = configId
        imAgentService.editAgent(imAgent as IMAgent)

        val changeImAgent = imAgentService.findByAgentId(imAgentId)
        assertNotNull(changeImAgent)
        assertEquals(agentId, changeImAgent?.agentId)
        assertEquals(nickName, changeImAgent?.nickName)
        assertEquals(configId, changeImAgent?.configId)
    }

    @Test
    fun removeAgent() {
        val mockRemoveAgent = mockAgent()
        imAgentService.editAgent(mockRemoveAgent)

        assertNotNull(mockRemoveAgent.id)
        val imAgentId = mockRemoveAgent.id as Long
        val agentRemoveBefore = imAgentService.findByAgentId(imAgentId)
        assertNotNull(agentRemoveBefore)

        imAgentService.removeAgent(imAgentId)

        val agentRemoveAfter = imAgentService.findByAgentId(imAgentId)
        assertNull(agentRemoveAfter)
    }

    @Test
    fun online() {
        val imAgentId = mockIMAgent.id as Long
        val beforeOnlineAgent = imAgentService.findByAgentId(imAgentId) as IMAgent
        assertFalse(beforeOnlineAgent.isOnline)

        imAgentService.online(imAgentId, !beforeOnlineAgent.isOnline)

        val afterOnlineAgent = imAgentService.findByAgentId(imAgentId) as IMAgent
        assertTrue(afterOnlineAgent.isOnline)

        imAgentService.online(imAgentId, !afterOnlineAgent.isOnline)

        val afterOfflineAgent = imAgentService.findByAgentId(imAgentId) as IMAgent
        assertFalse(afterOfflineAgent.isOnline)
    }

    @Test
    fun enable() {
        val imAgentId = mockIMAgent.id as Long
        val beforeDisabledAgent = imAgentService.findByAgentId(imAgentId) as IMAgent
        assertTrue(beforeDisabledAgent.enabled)

        imAgentService.enable(imAgentId, !beforeDisabledAgent.enabled)

        val afterDisabledAgent = imAgentService.findByAgentId(imAgentId) as IMAgent
        assertFalse(afterDisabledAgent.enabled)

        imAgentService.enable(imAgentId, !afterDisabledAgent.enabled)

        val afterEnabledAgent = imAgentService.findByAgentId(imAgentId) as IMAgent
        assertTrue(afterEnabledAgent.enabled)
    }

    private fun mockAgent(): IMAgent {
        return IMAgent(agentId = "agentId${UUID.randomUUID()}"
                , nickName = "nickName${UUID.randomUUID()}"
                , configId = "configId${UUID.randomUUID()}")
    }

}