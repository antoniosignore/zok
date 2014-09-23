package com.dtmc.finance.finpojo.derivative.equity



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(VanillaController)
@Mock([ArrestedUser,ArrestedToken])

class VanillaControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Vanilla VanillaTest

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
        params.Vanilla = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Vanilla_created"
        assertNotNull(response.json.id)
        assertNotNull(Vanilla.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Vanilla = [id:1]
        controller.update()
        assertEquals(response.json.response,"Vanilla_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Vanilla_deleted")
        assertNull(Vanilla.findById(params.id as Long))
    }
}