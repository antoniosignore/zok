package zok



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(UserPropertiesController)
@Mock([ArrestedUser,ArrestedToken])

class UserPropertiesControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    UserProperties UserPropertiesTest

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
        params.UserProperties = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "UserProperties_created"
        assertNotNull(response.json.id)
        assertNotNull(UserProperties.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.UserProperties = [id:1]
        controller.update()
        assertEquals(response.json.response,"UserProperties_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"UserProperties_deleted")
        assertNull(UserProperties.findById(params.id as Long))
    }
}