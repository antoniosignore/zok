package com.dtmc.finance.finpojo



import static org.junit.Assert.*
import org.junit.*

class ForecastControllerIntegrationTest {
    def ForecastControllerTest,tokenAdmin

    @Before
     void setUp() {

        ForecastControllerTest = new ForecastController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        ForecastControllerTest.params.id =  1
        ForecastControllerTest.getById()
        assertNotNull(ForecastControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        ForecastControllerTest.getAll()
        assertNotNull(ForecastControllerTest.response)
        assert ForecastControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        ForecastControllerTest.params.Forecast = [] //We must type here the required attributes for the class
        ForecastControllerTest.create()
        assertEquals(ForecastControllerTest.response.json.response,"Forecast_created")
        assert ForecastControllerTest.response != null
        assertNotNull(Forecast.findById(ForecastControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        ForecastControllerTest.params.Forecast = [id: 1L]//We need to search for a specific attribute of the class
        ForecastControllerTest.update()
        assertEquals(ForecastControllerTest.response.json?.response,"Forecast_updated")
    }

    @Test
    void testDelete(){
        ForecastControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        ForecastControllerTest.delete()
        assertEquals(response.json.response,"Forecast_deleted")
        assertNull(Forecast.findById(ForecastControllerTest.params.id as Long))
    }
}