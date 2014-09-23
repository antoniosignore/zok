package com.dtmc.finance.finpojo.derivative.equity



import static org.junit.Assert.*
import org.junit.*

class VanillaControllerIntegrationTest {
    def VanillaControllerTest,tokenAdmin

    @Before
     void setUp() {

        VanillaControllerTest = new VanillaController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        VanillaControllerTest.params.id =  1
        VanillaControllerTest.getById()
        assertNotNull(VanillaControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        VanillaControllerTest.getAll()
        assertNotNull(VanillaControllerTest.response)
        assert VanillaControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        VanillaControllerTest.params.Vanilla = [] //We must type here the required attributes for the class
        VanillaControllerTest.create()
        assertEquals(VanillaControllerTest.response.json.response,"Vanilla_created")
        assert VanillaControllerTest.response != null
        assertNotNull(Vanilla.findById(VanillaControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        VanillaControllerTest.params.Vanilla = [id: 1L]//We need to search for a specific attribute of the class
        VanillaControllerTest.update()
        assertEquals(VanillaControllerTest.response.json?.response,"Vanilla_updated")
    }

    @Test
    void testDelete(){
        VanillaControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        VanillaControllerTest.delete()
        assertEquals(response.json.response,"Vanilla_deleted")
        assertNull(Vanilla.findById(VanillaControllerTest.params.id as Long))
    }
}