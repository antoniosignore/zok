package com.dtmc.finance.finpojo



import static org.junit.Assert.*
import org.junit.*

class InstrumentControllerIntegrationTest {
    def InstrumentControllerTest,tokenAdmin

    @Before
     void setUp() {

        InstrumentControllerTest = new InstrumentController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        InstrumentControllerTest.params.id =  1
        InstrumentControllerTest.getById()
        assertNotNull(InstrumentControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        InstrumentControllerTest.getAll()
        assertNotNull(InstrumentControllerTest.response)
        assert InstrumentControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        InstrumentControllerTest.params.Instrument = [] //We must type here the required attributes for the class
        InstrumentControllerTest.create()
        assertEquals(InstrumentControllerTest.response.json.response,"Instrument_created")
        assert InstrumentControllerTest.response != null
        assertNotNull(Instrument.findById(InstrumentControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        InstrumentControllerTest.params.Instrument = [id: 1L]//We need to search for a specific attribute of the class
        InstrumentControllerTest.update()
        assertEquals(InstrumentControllerTest.response.json?.response,"Instrument_updated")
    }

    @Test
    void testDelete(){
        InstrumentControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        InstrumentControllerTest.delete()
        assertEquals(response.json.response,"Instrument_deleted")
        assertNull(Instrument.findById(InstrumentControllerTest.params.id as Long))
    }
}