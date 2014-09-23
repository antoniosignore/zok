package com.dtmc.club



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(MemberController)
@Mock([ArrestedUser,ArrestedToken])

class MemberControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Member MemberTest

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
        params.Member = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Member_created"
        assertNotNull(response.json.id)
        assertNotNull(Member.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Member = [id:1]
        controller.update()
        assertEquals(response.json.response,"Member_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Member_deleted")
        assertNull(Member.findById(params.id as Long))
    }
}