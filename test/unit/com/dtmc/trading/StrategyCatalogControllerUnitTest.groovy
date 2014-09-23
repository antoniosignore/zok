package com.dtmc.trading



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(StrategyCatalogController)
@Mock([ArrestedUser,ArrestedToken])

class StrategyCatalogControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    StrategyCatalog StrategyCatalogTest

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
        params.StrategyCatalog = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "StrategyCatalog_created"
        assertNotNull(response.json.id)
        assertNotNull(StrategyCatalog.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.StrategyCatalog = [id:1]
        controller.update()
        assertEquals(response.json.response,"StrategyCatalog_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"StrategyCatalog_deleted")
        assertNull(StrategyCatalog.findById(params.id as Long))
    }
}