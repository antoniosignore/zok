package com.dtmc.trading



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(SignalController)
@Mock([ArrestedUser,ArrestedToken])

class SignalControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Signal SignalTest

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
        params.Signal = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Signal_created"
        assertNotNull(response.json.id)
        assertNotNull(Signal.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Signal = [id:1]
        controller.update()
        assertEquals(response.json.response,"Signal_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Signal_deleted")
        assertNull(Signal.findById(params.id as Long))
    }
}