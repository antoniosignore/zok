package com.dtmc.finance.finpojo



import static org.junit.Assert.*
import org.junit.*

class TradeControllerIntegrationTest {
    def TradeControllerTest,tokenAdmin

    @Before
     void setUp() {

        TradeControllerTest = new TradeController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        TradeControllerTest.params.id =  1
        TradeControllerTest.getById()
        assertNotNull(TradeControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        TradeControllerTest.getAll()
        assertNotNull(TradeControllerTest.response)
        assert TradeControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        TradeControllerTest.params.Trade = [] //We must type here the required attributes for the class
        TradeControllerTest.create()
        assertEquals(TradeControllerTest.response.json.response,"Trade_created")
        assert TradeControllerTest.response != null
        assertNotNull(Trade.findById(TradeControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        TradeControllerTest.params.Trade = [id: 1L]//We need to search for a specific attribute of the class
        TradeControllerTest.update()
        assertEquals(TradeControllerTest.response.json?.response,"Trade_updated")
    }

    @Test
    void testDelete(){
        TradeControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        TradeControllerTest.delete()
        assertEquals(response.json.response,"Trade_deleted")
        assertNull(Trade.findById(TradeControllerTest.params.id as Long))
    }
}