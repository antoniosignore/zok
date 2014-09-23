package com.dtmc.finance.finpojo



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(EntryController)
@Mock([ArrestedUser,ArrestedToken])

class EntryControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Entry EntryTest

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
        params.Entry = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Entry_created"
        assertNotNull(response.json.id)
        assertNotNull(Entry.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Entry = [id:1]
        controller.update()
        assertEquals(response.json.response,"Entry_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Entry_deleted")
        assertNull(Entry.findById(params.id as Long))
    }
}