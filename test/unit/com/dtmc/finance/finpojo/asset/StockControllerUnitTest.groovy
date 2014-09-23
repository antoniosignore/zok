package com.dtmc.finance.finpojo.asset



import org.junit.*
import static org.junit.Assert.*
import grails.test.mixin.*
import arrested.ArrestedUser
import arrested.ArrestedToken
import grails.test.mixin.support.*

@TestFor(StockController)
@Mock([ArrestedUser,ArrestedToken])

class StockControllerUnitTest {
    ArrestedUser user
    ArrestedToken token
    Stock StockTest

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
        params.Stock = [] //We must type here the required attributes for the class
        controller.create()
        assert response.json?.response == "Stock_created"
        assertNotNull(response.json.id)
        assertNotNull(Stock.findById(response.json.id as Long))
    }

    void testUpdate(){
        params.Stock = [id:1]
        controller.update()
        assertEquals(response.json.response,"Stock_updated")
    }

    void testDelete(){
        params.id = 1
        controller.delete()
        assertEquals(response.json.response,"Stock_deleted")
        assertNull(Stock.findById(params.id as Long))
    }
}