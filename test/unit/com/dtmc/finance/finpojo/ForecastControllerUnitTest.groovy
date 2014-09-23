package com.dtmc.finance.finpojo



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(ForecastController)
@Mock([ArrestedUser,ArrestedToken])

class ForecastControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Forecast ForecastTest

    void setUp() {
        // Tear down logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testGetAll(){
        controller.getAll()
        assertNotNull(response)
        assert response.json?.id.size() >= 0
    }

    void testCreate(){
        params.Forecast = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Forecast_created"
        assertNotNull(response.json.id)
        assertNotNull(Forecast.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Forecast = [id:1]
        controller.update()
        assertEquals(response.json.response,"Forecast_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Forecast_deleted")
        assertNull(Forecast.findById(params.id as Long))
    }
}