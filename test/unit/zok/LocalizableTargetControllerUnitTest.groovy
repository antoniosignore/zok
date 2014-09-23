package zok



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(LocalizableTargetController)
@Mock([ArrestedUser,ArrestedToken])

class LocalizableTargetControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    LocalizableTarget LocalizableTargetTest

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
        params.LocalizableTarget = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "LocalizableTarget_created"
        assertNotNull(response.json.id)
        assertNotNull(LocalizableTarget.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.LocalizableTarget = [id:1]
        controller.update()
        assertEquals(response.json.response,"LocalizableTarget_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"LocalizableTarget_deleted")
        assertNull(LocalizableTarget.findById(params.id as Long))
    }
}