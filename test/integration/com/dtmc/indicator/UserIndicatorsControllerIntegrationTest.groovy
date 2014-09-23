package com.dtmc.indicator



import static org.junit.Assert.*
import org.junit.*

class UserIndicatorsControllerIntegrationTest {
    def UserIndicatorsControllerTest,tokenAdmin

    @Before
     void setUp() {

        UserIndicatorsControllerTest = new UserIndicatorsController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        UserIndicatorsControllerTest.params.id =  1
        UserIndicatorsControllerTest.getById()
        assertNotNull(UserIndicatorsControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        UserIndicatorsControllerTest.getAll()
        assertNotNull(UserIndicatorsControllerTest.response)
        assert UserIndicatorsControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        UserIndicatorsControllerTest.params.UserIndicators = [] //We must type here the required attributes for the class
        UserIndicatorsControllerTest.create()
        assertEquals(UserIndicatorsControllerTest.response.json.response,"UserIndicators_created")
        assert UserIndicatorsControllerTest.response != null
        assertNotNull(UserIndicators.findById(UserIndicatorsControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        UserIndicatorsControllerTest.params.UserIndicators = [id: 1L]//We need to search for a specific attribute of the class
        UserIndicatorsControllerTest.update()
        assertEquals(UserIndicatorsControllerTest.response.json?.response,"UserIndicators_updated")
    }

    @Test
    void testDelete(){
        UserIndicatorsControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        UserIndicatorsControllerTest.delete()
        assertEquals(response.json.response,"UserIndicators_deleted")
        assertNull(UserIndicators.findById(UserIndicatorsControllerTest.params.id as Long))
    }
}