package com.dtmc.finance.finpojo



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(InstrumentController)
@Mock([ArrestedUser,ArrestedToken])

class InstrumentControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Instrument InstrumentTest

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
        params.Instrument = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Instrument_created"
        assertNotNull(response.json.id)
        assertNotNull(Instrument.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Instrument = [id:1]
        controller.update()
        assertEquals(response.json.response,"Instrument_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Instrument_deleted")
        assertNull(Instrument.findById(params.id as Long))
    }
}