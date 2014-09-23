package com.dtmc.trading



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(StrategyExecutionController)
@Mock([ArrestedUser,ArrestedToken])

class StrategyExecutionControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    StrategyExecution StrategyExecutionTest

    void setUp() {
        // Tear down logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testGetAll(){
        controller.getAll()
        assertNotNull(response)
        assert response.json?.id.size() >= 0
    }

    void testCreate(){
        params.StrategyExecution = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "StrategyExecution_created"
        assertNotNull(response.json.id)
        assertNotNull(StrategyExecution.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.StrategyExecution = [id:1]
        controller.update()
        assertEquals(response.json.response,"StrategyExecution_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"StrategyExecution_deleted")
        assertNull(StrategyExecution.findById(params.id as Long))
    }
}