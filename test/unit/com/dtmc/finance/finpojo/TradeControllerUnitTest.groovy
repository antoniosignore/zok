package com.dtmc.finance.finpojo



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(TradeController)
@Mock([ArrestedUser,ArrestedToken])

class TradeControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Trade TradeTest

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
        params.Trade = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Trade_created"
        assertNotNull(response.json.id)
        assertNotNull(Trade.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Trade = [id:1]
        controller.update()
        assertEquals(response.json.response,"Trade_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Trade_deleted")
        assertNull(Trade.findById(params.id as Long))
    }
}