package com.dtmc.finance.finpojo.asset



import static org.junit.Assert.*
import org.junit.*

class StockControllerIntegrationTest {
    def StockControllerTest,tokenAdmin

    @Before
     void setUp() {

        StockControllerTest = new StockController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        StockControllerTest.params.id =  1
        StockControllerTest.getById()
        assertNotNull(StockControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        StockControllerTest.getAll()
        assertNotNull(StockControllerTest.response)
        assert StockControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        StockControllerTest.params.Stock = [] //We must type here the required attributes for the class
        StockControllerTest.create()
        assertEquals(StockControllerTest.response.json.response,"Stock_created")
        assert StockControllerTest.response != null
        assertNotNull(Stock.findById(StockControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        StockControllerTest.params.Stock = [id: 1L]//We need to search for a specific attribute of the class
        StockControllerTest.update()
        assertEquals(StockControllerTest.response.json?.response,"Stock_updated")
    }

    @Test
    void testDelete(){
        StockControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        StockControllerTest.delete()
        assertEquals(response.json.response,"Stock_deleted")
        assertNull(Stock.findById(StockControllerTest.params.id as Long))
    }
}