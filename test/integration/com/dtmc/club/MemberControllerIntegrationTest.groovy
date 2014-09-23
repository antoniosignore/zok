package com.dtmc.club



import static org.junit.Assert.*
import org.junit.*

class MemberControllerIntegrationTest {
    def MemberControllerTest,tokenAdmin

    @Before
     void setUp() {

        MemberControllerTest = new MemberController()
        tokenAdmin = ArrestedUser.findByUsername('user@test.me')?.token.token
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetById(){
        MemberControllerTest.params.id =  1
        MemberControllerTest.getById()
        assertNotNull(MemberControllerTest.response.json)
    }

    @Test
    void testGetAll(){
        MemberControllerTest.getAll()
        assertNotNull(MemberControllerTest.response)
        assert MemberControllerTest.response.json?.id.size() >= 0
    }

    @Test
    void testCreate() {
        MemberControllerTest.params.Member = [] //We must type here the required attributes for the class
        MemberControllerTest.create()
        assertEquals(MemberControllerTest.response.json.response,"Member_created")
        assert MemberControllerTest.response != null
        assertNotNull(Member.findById(MemberControllerTest.response.json.id))
    }

    @Test
    void testUpdate() {
        MemberControllerTest.params.Member = [id: 1L]//We need to search for a specific attribute of the class
        MemberControllerTest.update()
        assertEquals(MemberControllerTest.response.json?.response,"Member_updated")
    }

    @Test
    void testDelete(){
        MemberControllerTest.params.id = 1L //We need to search for a specific attribute of the class
        MemberControllerTest.delete()
        assertEquals(response.json.response,"Member_deleted")
        assertNull(Member.findById(MemberControllerTest.params.id as Long))
    }
}