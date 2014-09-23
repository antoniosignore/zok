package zok



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(MyTestController)
@Mock([ArrestedUser,ArrestedToken])

class MyTestControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    MyTest MyTestTest

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
        params.MyTest = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "MyTest_created"
        assertNotNull(response.json.id)
        assertNotNull(MyTest.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.MyTest = [id:1]
        controller.update()
        assertEquals(response.json.response,"MyTest_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"MyTest_deleted")
        assertNull(MyTest.findById(params.id as Long))
    }
}