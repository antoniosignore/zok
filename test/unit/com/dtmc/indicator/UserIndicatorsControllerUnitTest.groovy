package com.dtmc.indicator



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(UserIndicatorsController)
@Mock([ArrestedUser,ArrestedToken])

class UserIndicatorsControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    UserIndicators UserIndicatorsTest

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
        params.UserIndicators = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "UserIndicators_created"
        assertNotNull(response.json.id)
        assertNotNull(UserIndicators.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.UserIndicators = [id:1]
        controller.update()
        assertEquals(response.json.response,"UserIndicators_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"UserIndicators_deleted")
        assertNull(UserIndicators.findById(params.id as Long))
    }
}