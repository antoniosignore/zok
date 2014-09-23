package com.dtmc.finance.finpojo



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(DailyController)
@Mock([ArrestedUser,ArrestedToken])

class DailyControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Daily DailyTest

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
        params.Daily = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Daily_created"
        assertNotNull(response.json.id)
        assertNotNull(Daily.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Daily = [id:1]
        controller.update()
        assertEquals(response.json.response,"Daily_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Daily_deleted")
        assertNull(Daily.findById(params.id as Long))
    }
}