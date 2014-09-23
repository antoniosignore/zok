package com.dtmc.trading



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(StrategySubscriptionController)
@Mock([ArrestedUser,ArrestedToken])

class StrategySubscriptionControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    StrategySubscription StrategySubscriptionTest

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
        params.StrategySubscription = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "StrategySubscription_created"
        assertNotNull(response.json.id)
        assertNotNull(StrategySubscription.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.StrategySubscription = [id:1]
        controller.update()
        assertEquals(response.json.response,"StrategySubscription_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"StrategySubscription_deleted")
        assertNull(StrategySubscription.findById(params.id as Long))
    }
}