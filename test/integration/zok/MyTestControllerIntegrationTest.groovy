package zok



import static org.junit.Assert.*
import org.junit.*

class MyTestControllerIntegrationTest {
    def MyTestControllerTest,tokenAdmin

    @Before
     void setUp() {

        MyTestControllerTest = new MyTestController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        MyTestControllerTest.params.id =  1
        MyTestControllerTest.getById()
        assertNotNull(MyTestControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        MyTestControllerTest.getAll()
        assertNotNull(MyTestControllerTest.response)
        assert MyTestControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        MyTestControllerTest.params.MyTest = [] //We must type here the required attributes for the class
        MyTestControllerTest.create()
        assertEquals(MyTestControllerTest.response.json.response,"MyTest_created")
        assert MyTestControllerTest.response != null
        assertNotNull(MyTest.findById(MyTestControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        MyTestControllerTest.params.MyTest = [id: 1L]//We need to search for a specific attribute of the class
        MyTestControllerTest.update()
        assertEquals(MyTestControllerTest.response.json?.response,"MyTest_updated")
    }

    @Test
    void testDelete(){
        MyTestControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        MyTestControllerTest.delete()
        assertEquals(response.json.response,"MyTest_deleted")
        assertNull(MyTest.findById(MyTestControllerTest.params.id as Long))
    }
}