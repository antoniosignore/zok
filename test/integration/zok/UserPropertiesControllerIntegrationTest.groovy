package zok



import static org.junit.Assert.*
import org.junit.*

class UserPropertiesControllerIntegrationTest {
    def UserPropertiesControllerTest,tokenAdmin

    @Before
     void setUp() {

        UserPropertiesControllerTest = new UserPropertiesController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        UserPropertiesControllerTest.params.id =  1
        UserPropertiesControllerTest.getById()
        assertNotNull(UserPropertiesControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        UserPropertiesControllerTest.getAll()
        assertNotNull(UserPropertiesControllerTest.response)
        assert UserPropertiesControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        UserPropertiesControllerTest.params.UserProperties = [] //We must type here the required attributes for the class
        UserPropertiesControllerTest.create()
        assertEquals(UserPropertiesControllerTest.response.json.response,"UserProperties_created")
        assert UserPropertiesControllerTest.response != null
        assertNotNull(UserProperties.findById(UserPropertiesControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        UserPropertiesControllerTest.params.UserProperties = [id: 1L]//We need to search for a specific attribute of the class
        UserPropertiesControllerTest.update()
        assertEquals(UserPropertiesControllerTest.response.json?.response,"UserProperties_updated")
    }

    @Test
    void testDelete(){
        UserPropertiesControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        UserPropertiesControllerTest.delete()
        assertEquals(response.json.response,"UserProperties_deleted")
        assertNull(UserProperties.findById(UserPropertiesControllerTest.params.id as Long))
    }
}