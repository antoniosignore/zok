package com.dtmc.trading



import static org.junit.Assert.*
import org.junit.*

class StrategySubscriptionControllerIntegrationTest {
    def StrategySubscriptionControllerTest,tokenAdmin

    @Before
     void setUp() {

        StrategySubscriptionControllerTest = new StrategySubscriptionController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        StrategySubscriptionControllerTest.params.id =  1
        StrategySubscriptionControllerTest.getById()
        assertNotNull(StrategySubscriptionControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        StrategySubscriptionControllerTest.getAll()
        assertNotNull(StrategySubscriptionControllerTest.response)
        assert StrategySubscriptionControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        StrategySubscriptionControllerTest.params.StrategySubscription = [] //We must type here the required attributes for the class
        StrategySubscriptionControllerTest.create()
        assertEquals(StrategySubscriptionControllerTest.response.json.response,"StrategySubscription_created")
        assert StrategySubscriptionControllerTest.response != null
        assertNotNull(StrategySubscription.findById(StrategySubscriptionControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        StrategySubscriptionControllerTest.params.StrategySubscription = [id: 1L]//We need to search for a specific attribute of the class
        StrategySubscriptionControllerTest.update()
        assertEquals(StrategySubscriptionControllerTest.response.json?.response,"StrategySubscription_updated")
    }

    @Test
    void testDelete(){
        StrategySubscriptionControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        StrategySubscriptionControllerTest.delete()
        assertEquals(response.json.response,"StrategySubscription_deleted")
        assertNull(StrategySubscription.findById(StrategySubscriptionControllerTest.params.id as Long))
    }
}