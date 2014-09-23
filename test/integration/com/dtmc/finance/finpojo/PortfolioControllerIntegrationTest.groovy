package com.dtmc.finance.finpojo



import static org.junit.Assert.*
import org.junit.*

class PortfolioControllerIntegrationTest {
    def PortfolioControllerTest,tokenAdmin

    @Before
     void setUp() {

        PortfolioControllerTest = new PortfolioController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        PortfolioControllerTest.params.id =  1
        PortfolioControllerTest.getById()
        assertNotNull(PortfolioControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        PortfolioControllerTest.getAll()
        assertNotNull(PortfolioControllerTest.response)
        assert PortfolioControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        PortfolioControllerTest.params.Portfolio = [] //We must type here the required attributes for the class
        PortfolioControllerTest.create()
        assertEquals(PortfolioControllerTest.response.json.response,"Portfolio_created")
        assert PortfolioControllerTest.response != null
        assertNotNull(Portfolio.findById(PortfolioControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        PortfolioControllerTest.params.Portfolio = [id: 1L]//We need to search for a specific attribute of the class
        PortfolioControllerTest.update()
        assertEquals(PortfolioControllerTest.response.json?.response,"Portfolio_updated")
    }

    @Test
    void testDelete(){
        PortfolioControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        PortfolioControllerTest.delete()
        assertEquals(response.json.response,"Portfolio_deleted")
        assertNull(Portfolio.findById(PortfolioControllerTest.params.id as Long))
    }
}