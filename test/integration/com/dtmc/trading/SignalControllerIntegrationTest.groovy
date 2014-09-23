package com.dtmc.trading



import static org.junit.Assert.*
import org.junit.*

class SignalControllerIntegrationTest {
    def SignalControllerTest,tokenAdmin

    @Before
     void setUp() {

        SignalControllerTest = new SignalController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        SignalControllerTest.params.id =  1
        SignalControllerTest.getById()
        assertNotNull(SignalControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        SignalControllerTest.getAll()
        assertNotNull(SignalControllerTest.response)
        assert SignalControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        SignalControllerTest.params.Signal = [] //We must type here the required attributes for the class
        SignalControllerTest.create()
        assertEquals(SignalControllerTest.response.json.response,"Signal_created")
        assert SignalControllerTest.response != null
        assertNotNull(Signal.findById(SignalControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        SignalControllerTest.params.Signal = [id: 1L]//We need to search for a specific attribute of the class
        SignalControllerTest.update()
        assertEquals(SignalControllerTest.response.json?.response,"Signal_updated")
    }

    @Test
    void testDelete(){
        SignalControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        SignalControllerTest.delete()
        assertEquals(response.json.response,"Signal_deleted")
        assertNull(Signal.findById(SignalControllerTest.params.id as Long))
    }
}