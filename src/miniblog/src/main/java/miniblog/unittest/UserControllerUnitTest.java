/**
 * 
 */
package miniblog.unittest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import miniblog.constant.URLConstant;
import miniblog.controller.UserController;
import miniblog.entity.Users;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author NguyenLeQuoc
 *
 */
@SuppressWarnings("deprecation")
public class UserControllerUnitTest {
    // Create object Usercontroller to test
    @Autowired
    private UserController userController;
    
    // Create object CommonController to support test
    @Before
    public void setUp() throws Exception
    {
        userController = new UserController();
    }

    @After
    public void tearDown() throws Exception
    {
        userController = null;
    }

    /**
     * Test method add user
     * 
     * @throws Exception
     */
    @Test
    public void testAddUser() throws Exception
    {
        // Field required!
        Users u = new Users();
        assertEquals(userController.addUser(u).getStatus(), 1001);

        //Delete user before, because after run this menthod test will have a user created//
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_USER);
        // accept with input type
        requestDelete.accept(MediaType.APPLICATION_FORM_URLENCODED);
        // input data
        String usernameInput = "username=testAdd";
        // get data
        requestDelete.body(MediaType.APPLICATION_FORM_URLENCODED, usernameInput);
        // run operation and get respond
        ClientResponse<String> response2 = requestDelete.post(String.class);
        // close environment
        response2.close();
        
        // ///////////////////Add new user/////////////////////////
        // create a request add user from client
        ClientRequest requestAddUser = new ClientRequest(URLConstant.CREATE_USER);
        // accept with input type
        requestAddUser.accept(MediaType.APPLICATION_JSON);
        // input data
        String userInput = "{\"username\": \"testAdd\"," + "\"password\": \"123456\","
                + "\"lastname\": \"one\",\"firstname\": \"test\"," + "\"email\":\"testadd@yahoo.com\"}";
        // get data
        requestAddUser.body(MediaType.APPLICATION_JSON, userInput);
        // run operation and get respond
        ClientResponse<Users> response = requestAddUser.post(Users.class);
        // check add new user with code 200 is successful!
        assertEquals(response.getStatus(), 200);
        // close environment
        response.close();

        // //////////////Add user again to check exist/////////////////
        // run operation again and get respond
        response = requestAddUser.post(Users.class);
        // check add new user with code 9002 is login faild!
        assertEquals(response.getStatus(), 9002);
        response.close();
    }

    /**
     * Test method user login
     * 
     * @throws Exception
     */
    @Test
    public void testLogin() throws Exception
    {
        // create a request from client
        ClientRequest request = new ClientRequest(URLConstant.LOGIN_USER);
        // accept with input type
        request.accept(MediaType.APPLICATION_JSON);
        // input data
        String input = "username=nguyen.viet&password=123456";
        // push data
        request.body(MediaType.APPLICATION_FORM_URLENCODED, input);
        // get respone to check
        ClientResponse<String> response = request.post(String.class);

        // /////////// check login user success!////////////////
        // return with code 200 is successful
        assertEquals(response.getStatus(), 200);
        // close response environment
        response.close();

        // ///////////////// check login faild////////////////////
        // set wrong password
        input = "username=nguyen.a&password=1234567";
        // push data
        request.body(MediaType.APPLICATION_FORM_URLENCODED, input);
        // get respone to check
        response = request.post(String.class);
        // return with code 9001 is faild
        assertEquals(response.getStatus(), 9001);
        // close response environment
        response.close();
    }

    /**
     * Test method user logout
     */
    @Test
    public void testLogout()
    {
        // get response with status 200, this successful!
        assertEquals(userController.logout().getStatus(), 200);
    }

    /**
     * Test method get user by id
     * 
     * @throws Exception
     */
    @Test
    public void testGetUserInfor() throws Exception
    {
        // create a request from client
        ClientRequest request = new ClientRequest(URLConstant.INFOR_USER+"1");
        // get respone to check
        ClientResponse<String> response = request.get(String.class);
        // /////////// get user infor ////////////////
        // return with code 200 is successful
        assertEquals(response.getStatus(), 200);
        // close response environment
        response.close();
    }

    /**
     * Test method update user by id
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateUser() throws Exception
    {
        // create a request from client
        ClientRequest request = new ClientRequest(URLConstant.UPDATE_USER);
        // accept with input type
        request.accept("application/json");
        // input data
        String userInput = "{\"id\": \"1\", " + "\"lastname\": \"nguyen\", " + "\"firstname\": \"viet\", "
                + "\"email\":\"viet@yahoo.com\"}";
        // get data
        request.body("application/json", userInput);
        // run operation and get respond
        ClientResponse<String> response = request.put(String.class);
        // check add new user with code 200 is successful!
        assertEquals(response.getStatus(), 200);
        // close environment
        response.close();
    }

    /**
     * Test method change password user by id
     * 
     * @throws Exception
     */
    @Test
    public void testChangePass() throws Exception
    {
        // create a request from client
        ClientRequest request = new ClientRequest(URLConstant.CHANGEPASS_USER);
        // accept with input type
        request.accept("application/json");
        // input data
        String userInput = "{\"id\": \"1\", " + "\"password\":\"123456\"}";
        // get data
        request.body("application/json", userInput);
        // run operation and get respond
        ClientResponse<String> response = request.put(String.class);
        // check add new user with code 200 is successful!
        assertEquals(response.getStatus(), 200);
        // close environment
        response.close();
    }

    /**
     * Test method get users by name
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByName() throws Exception
    {
        // create a request from client
        ClientRequest request = new ClientRequest(URLConstant.SEARCH_USER);
        // accept with input type
        request.accept(MediaType.APPLICATION_JSON);
        // input data
        String input = "name=viet";
        // push data
        request.body(MediaType.APPLICATION_FORM_URLENCODED, input);
        // get respone to check
        ClientResponse<String> response = request.post(String.class);

        // /////////// check find users////////////////

        if (response.getStatus() == 200)
            // return with code 200 is successful
            assertEquals(response.getStatus(), 200);
        else
            // return 9001 if user not exist
            assertEquals(response.getStatus(), 9001);
        // close response environment
        response.close();
    }
}
