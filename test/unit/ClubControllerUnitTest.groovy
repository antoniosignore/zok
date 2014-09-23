

import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(ClubController)
@Mock([ArrestedUser,ArrestedToken])

class ClubControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Club ClubTest

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
        params.Club = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Club_created"
        assertNotNull(response.json.id)
        assertNotNull(Club.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Club = [id:1]
        controller.update()
        assertEquals(response.json.response,"Club_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Club_deleted")
        assertNull(Club.findById(params.id as Long))
    }
}