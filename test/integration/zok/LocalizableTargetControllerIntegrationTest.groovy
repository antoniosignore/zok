package zok



import static org.junit.Assert.*
import org.junit.*

class LocalizableTargetControllerIntegrationTest {
    def LocalizableTargetControllerTest,tokenAdmin

    @Before
     void setUp() {

        LocalizableTargetControllerTest = new LocalizableTargetController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        LocalizableTargetControllerTest.params.id =  1
        LocalizableTargetControllerTest.getById()
        assertNotNull(LocalizableTargetControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        LocalizableTargetControllerTest.getAll()
        assertNotNull(LocalizableTargetControllerTest.response)
        assert LocalizableTargetControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        LocalizableTargetControllerTest.params.LocalizableTarget = [] //We must type here the required attributes for the class
        LocalizableTargetControllerTest.create()
        assertEquals(LocalizableTargetControllerTest.response.json.response,"LocalizableTarget_created")
        assert LocalizableTargetControllerTest.response != null
        assertNotNull(LocalizableTarget.findById(LocalizableTargetControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        LocalizableTargetControllerTest.params.LocalizableTarget = [id: 1L]//We need to search for a specific attribute of the class
        LocalizableTargetControllerTest.update()
        assertEquals(LocalizableTargetControllerTest.response.json?.response,"LocalizableTarget_updated")
    }

    @Test
    void testDelete(){
        LocalizableTargetControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        LocalizableTargetControllerTest.delete()
        assertEquals(response.json.response,"LocalizableTarget_deleted")
        assertNull(LocalizableTarget.findById(LocalizableTargetControllerTest.params.id as Long))
    }
}