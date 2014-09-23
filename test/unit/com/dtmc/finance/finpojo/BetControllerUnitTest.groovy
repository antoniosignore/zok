package com.dtmc.finance.finpojo



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(BetController)
@Mock([ArrestedUser,ArrestedToken])

class BetControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Bet BetTest

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
        params.Bet = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Bet_created"
        assertNotNull(response.json.id)
        assertNotNull(Bet.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Bet = [id:1]
        controller.update()
        assertEquals(response.json.response,"Bet_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Bet_deleted")
        assertNull(Bet.findById(params.id as Long))
    }
}