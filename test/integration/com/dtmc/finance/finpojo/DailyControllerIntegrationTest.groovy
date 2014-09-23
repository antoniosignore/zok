package com.dtmc.finance.finpojo



import static org.junit.Assert.*
import org.junit.*

class DailyControllerIntegrationTest {
    def DailyControllerTest,tokenAdmin

    @Before
     void setUp() {

        DailyControllerTest = new DailyController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        DailyControllerTest.params.id =  1
        DailyControllerTest.getById()
        assertNotNull(DailyControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        DailyControllerTest.getAll()
        assertNotNull(DailyControllerTest.response)
        assert DailyControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        DailyControllerTest.params.Daily = [] //We must type here the required attributes for the class
        DailyControllerTest.create()
        assertEquals(DailyControllerTest.response.json.response,"Daily_created")
        assert DailyControllerTest.response != null
        assertNotNull(Daily.findById(DailyControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        DailyControllerTest.params.Daily = [id: 1L]//We need to search for a specific attribute of the class
        DailyControllerTest.update()
        assertEquals(DailyControllerTest.response.json?.response,"Daily_updated")
    }

    @Test
    void testDelete(){
        DailyControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        DailyControllerTest.delete()
        assertEquals(response.json.response,"Daily_deleted")
        assertNull(Daily.findById(DailyControllerTest.params.id as Long))
    }
}