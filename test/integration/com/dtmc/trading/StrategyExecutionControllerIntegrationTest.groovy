package com.dtmc.trading



import static org.junit.Assert.*
import org.junit.*

class StrategyExecutionControllerIntegrationTest {
    def StrategyExecutionControllerTest,tokenAdmin

    @Before
     void setUp() {

        StrategyExecutionControllerTest = new StrategyExecutionController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        StrategyExecutionControllerTest.params.id =  1
        StrategyExecutionControllerTest.getById()
        assertNotNull(StrategyExecutionControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        StrategyExecutionControllerTest.getAll()
        assertNotNull(StrategyExecutionControllerTest.response)
        assert StrategyExecutionControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        StrategyExecutionControllerTest.params.StrategyExecution = [] //We must type here the required attributes for the class
        StrategyExecutionControllerTest.create()
        assertEquals(StrategyExecutionControllerTest.response.json.response,"StrategyExecution_created")
        assert StrategyExecutionControllerTest.response != null
        assertNotNull(StrategyExecution.findById(StrategyExecutionControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        StrategyExecutionControllerTest.params.StrategyExecution = [id: 1L]//We need to search for a specific attribute of the class
        StrategyExecutionControllerTest.update()
        assertEquals(StrategyExecutionControllerTest.response.json?.response,"StrategyExecution_updated")
    }

    @Test
    void testDelete(){
        StrategyExecutionControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        StrategyExecutionControllerTest.delete()
        assertEquals(response.json.response,"StrategyExecution_deleted")
        assertNull(StrategyExecution.findById(StrategyExecutionControllerTest.params.id as Long))
    }
}