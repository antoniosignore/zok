

import static org.junit.Assert.*
import org.junit.*

class ClubControllerIntegrationTest {
    def ClubControllerTest,tokenAdmin

    @Before
     void setUp() {

        ClubControllerTest = new ClubController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        ClubControllerTest.params.id =  1
        ClubControllerTest.getById()
        assertNotNull(ClubControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        ClubControllerTest.getAll()
        assertNotNull(ClubControllerTest.response)
        assert ClubControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        ClubControllerTest.params.Club = [] //We must type here the required attributes for the class
        ClubControllerTest.create()
        assertEquals(ClubControllerTest.response.json.response,"Club_created")
        assert ClubControllerTest.response != null
        assertNotNull(Club.findById(ClubControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        ClubControllerTest.params.Club = [id: 1L]//We need to search for a specific attribute of the class
        ClubControllerTest.update()
        assertEquals(ClubControllerTest.response.json?.response,"Club_updated")
    }

    @Test
    void testDelete(){
        ClubControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        ClubControllerTest.delete()
        assertEquals(response.json.response,"Club_deleted")
        assertNull(Club.findById(ClubControllerTest.params.id as Long))
    }
}