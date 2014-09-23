package com.dtmc.finance.finpojo



import static org.junit.Assert.*
import org.junit.*

class EntryControllerIntegrationTest {
    def EntryControllerTest,tokenAdmin

    @Before
     void setUp() {

        EntryControllerTest = new EntryController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        EntryControllerTest.params.id =  1
        EntryControllerTest.getById()
        assertNotNull(EntryControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        EntryControllerTest.getAll()
        assertNotNull(EntryControllerTest.response)
        assert EntryControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        EntryControllerTest.params.Entry = [] //We must type here the required attributes for the class
        EntryControllerTest.create()
        assertEquals(EntryControllerTest.response.json.response,"Entry_created")
        assert EntryControllerTest.response != null
        assertNotNull(Entry.findById(EntryControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        EntryControllerTest.params.Entry = [id: 1L]//We need to search for a specific attribute of the class
        EntryControllerTest.update()
        assertEquals(EntryControllerTest.response.json?.response,"Entry_updated")
    }

    @Test
    void testDelete(){
        EntryControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        EntryControllerTest.delete()
        assertEquals(response.json.response,"Entry_deleted")
        assertNull(Entry.findById(EntryControllerTest.params.id as Long))
    }
}