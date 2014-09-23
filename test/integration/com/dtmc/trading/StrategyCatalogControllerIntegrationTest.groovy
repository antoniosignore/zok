package com.dtmc.trading



import static org.junit.Assert.*
import org.junit.*

class StrategyCatalogControllerIntegrationTest {
    def StrategyCatalogControllerTest,tokenAdmin

    @Before
     void setUp() {

        StrategyCatalogControllerTest = new StrategyCatalogController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        StrategyCatalogControllerTest.params.id =  1
        StrategyCatalogControllerTest.getById()
        assertNotNull(StrategyCatalogControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        StrategyCatalogControllerTest.getAll()
        assertNotNull(StrategyCatalogControllerTest.response)
        assert StrategyCatalogControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        StrategyCatalogControllerTest.params.StrategyCatalog = [] //We must type here the required attributes for the class
        StrategyCatalogControllerTest.create()
        assertEquals(StrategyCatalogControllerTest.response.json.response,"StrategyCatalog_created")
        assert StrategyCatalogControllerTest.response != null
        assertNotNull(StrategyCatalog.findById(StrategyCatalogControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        StrategyCatalogControllerTest.params.StrategyCatalog = [id: 1L]//We need to search for a specific attribute of the class
        StrategyCatalogControllerTest.update()
        assertEquals(StrategyCatalogControllerTest.response.json?.response,"StrategyCatalog_updated")
    }

    @Test
    void testDelete(){
        StrategyCatalogControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        StrategyCatalogControllerTest.delete()
        assertEquals(response.json.response,"StrategyCatalog_deleted")
        assertNull(StrategyCatalog.findById(StrategyCatalogControllerTest.params.id as Long))
    }
}