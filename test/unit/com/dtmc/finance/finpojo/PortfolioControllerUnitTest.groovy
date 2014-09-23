package com.dtmc.finance.finpojo



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(PortfolioController)
@Mock([ArrestedUser,ArrestedToken])

class PortfolioControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Portfolio PortfolioTest

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
        params.Portfolio = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Portfolio_created"
        assertNotNull(response.json.id)
        assertNotNull(Portfolio.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Portfolio = [id:1]
        controller.update()
        assertEquals(response.json.response,"Portfolio_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Portfolio_deleted")
        assertNull(Portfolio.findById(params.id as Long))
    }
}