package com.dtmc.finance.finpojo



import static org.junit.Assert.*
import org.junit.*

class BetControllerIntegrationTest {
    def BetControllerTest,tokenAdmin

    @Before
     void setUp() {

        BetControllerTest = new BetController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        BetControllerTest.params.id =  1
        BetControllerTest.getById()
        assertNotNull(BetControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        BetControllerTest.getAll()
        assertNotNull(BetControllerTest.response)
        assert BetControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        BetControllerTest.params.Bet = [] //We must type here the required attributes for the class
        BetControllerTest.create()
        assertEquals(BetControllerTest.response.json.response,"Bet_created")
        assert BetControllerTest.response != null
        assertNotNull(Bet.findById(BetControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        BetControllerTest.params.Bet = [id: 1L]//We need to search for a specific attribute of the class
        BetControllerTest.update()
        assertEquals(BetControllerTest.response.json?.response,"Bet_updated")
    }

    @Test
    void testDelete(){
        BetControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        BetControllerTest.delete()
        assertEquals(response.json.response,"Bet_deleted")
        assertNull(Bet.findById(BetControllerTest.params.id as Long))
    }
}