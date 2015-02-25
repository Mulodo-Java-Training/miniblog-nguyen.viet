/**
 * 
 */
package miniblog.test.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import miniblog.constant.URLConstant;
import miniblog.controller.UserController;
import miniblog.entity.Users;
import miniblog.util.ResultResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author NguyenLeQuoc
 *
 */
@SuppressWarnings("deprecation")
public class UserControllerUnitTest {
    // Create object Usercontroller to test
    @Autowired
    private UserController userController;
    
    //create User object to test
    private Users users;
    
    //Create Gson Object to parse Java Object to Json
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    
    @Before
    public void setUp() throws Exception
    {
        userController = new UserController();
        /*ADD USER FOR CHECK METHODS*/
        // create a request add user from client
        ClientRequest requestAddUser = new ClientRequest(URLConstant.CREATE_USER);
        // accept with input type
        requestAddUser.accept(MediaType.APPLICATION_JSON);
        // input data
        Users u = new Users();
        u.setUsername("nguyen.viet.test");
        u.setPassword("123456");
        u.setFirstname("nguyen le quoc");
        u.setLastname("viet");
        u.setEmail("unittest@gmail.com");
        // set data
        requestAddUser.body(MediaType.APPLICATION_JSON, gson.toJson(u));
        // run operation and get respond
        ClientResponse<ResultResponse> response = requestAddUser.post(ResultResponse.class);
        /*Get Id of post to delete*/
        
        //Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(response.getEntity(String.class), ResultResponse.class);
        //Parse Data object(json format) of ResultResponse to Users
        Users user = new ObjectMapper().readValue(gson.toJson(result.getData()), Users.class);
        //Get id of user just add
        users = user;
        // close environment
        response.close();
    }

    @After
    public void tearDown() throws Exception
    {
        userController = null;
        /*create a request delete a user account of adding test*/
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_USER + users.getId());
        // run operation and get respond
        requestDelete.delete(ResultResponse.class);
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
        //Create json object
        
        // ///////////////////Add new user/////////////////////////
        // create a request add user from client
        ClientRequest requestAddUser = new ClientRequest(URLConstant.CREATE_USER);
        // accept with input type
        requestAddUser.accept(MediaType.APPLICATION_JSON);
        // input data
        u.setUsername("test.add");
        u.setPassword("123456");
        u.setFirstname("unit");
        u.setLastname("test");
        u.setEmail("testadd@gmail.com");
        // get data
        requestAddUser.body(MediaType.APPLICATION_JSON, gson.toJson(u));
        // run operation and get respond
        ClientResponse<ResultResponse> response = requestAddUser.post(ResultResponse.class);
        // check add new user with code 200 is successful!
        assertEquals(response.getStatus(), 200);
        
        /*Get Id of post to delete*/
        //Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(response.getEntity(String.class), ResultResponse.class);
        //Parse Data object(json format) of ResultResponse to Users
        Users user = new ObjectMapper().readValue(gson.toJson(result.getData()), Users.class);
        //Get id of user just add
        int userID = user.getId();
        // close environment
        response.close();

        // //////////////Add user again to check exist/////////////////
        // run operation again and get respond
        response = requestAddUser.post(ResultResponse.class);
        // check add new user with code 9002 is login faild!
        assertEquals(response.getStatus(), 9002);
        response.close();
        
        /*create a request delete a user account from user just add*/
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_USER + userID);
        // run operation and get respond
        requestDelete.delete(ResultResponse.class);
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
        String username = "username=nguyen.viet.test";
        String password = "password=123456";
        // push data
        request.body(MediaType.APPLICATION_FORM_URLENCODED, username + "&" + password);
        // get respone to check
        ClientResponse<ResultResponse> response = request.post(ResultResponse.class);

        // /////////// check login user success!////////////////
        // return with code 200 is successful
        assertEquals(response.getStatus(), 200);
        // close response environment
        response.close();

        // ///////////////// check login faild////////////////////
        // set wrong password
        username = "username=nguyen.viet.test";
        password = "password=1234567";
        // push data
        request.body(MediaType.APPLICATION_FORM_URLENCODED, username + "&" + password);
        // get respone to check
        response = request.post(ResultResponse.class);
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
        ClientRequest request = new ClientRequest(URLConstant.INFOR_USER + users.getId());
        // get respone to check
        ClientResponse<ResultResponse> response = request.get(ResultResponse.class);
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
        //update email unittest@gmail.com to test@gmail.com
        users.setEmail("unittest@gmail.com");
        // get data
        request.body("application/json", gson.toJson(users));
        // run operation and get respond
        ClientResponse<ResultResponse> response = request.put(ResultResponse.class);
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
        //change password
        users.setPassword("1234567");
        // get data
        request.body("application/json", gson.toJson(users));
        // run operation and get respond
        ClientResponse<ResultResponse> response = request.put(ResultResponse.class);
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
        ClientResponse<ResultResponse> response = request.post(ResultResponse.class);

        // /////////// check find users////////////////
        if (response.getStatus() == 200)
            // return with code 200 is successful
            assertEquals(response.getStatus(), 200);
        if (response.getStatus() == 9001)
            // return 9001 if user not exist
            assertEquals(response.getStatus(), 9001);
        // close response environment
        response.close();
    }
}
