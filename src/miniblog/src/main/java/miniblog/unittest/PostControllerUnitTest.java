package miniblog.unittest;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import miniblog.constant.URLConstant;
import miniblog.controller.PostController;
import miniblog.controller.UserController;
import miniblog.entity.Articles;

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
public class PostControllerUnitTest {
    @Autowired
    private PostController postController;
    private UserController userController;

    // Create object CommonController to support test
    @Before
    public void setUp() throws Exception
    {
        postController = new PostController();
        userController = new UserController();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * Test method add Post
     * 
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testAddPost() throws Exception
    {
        // User Login
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

        // /////////////////Check create a post success/////////////////
        // create a request add post from user
        ClientRequest requestAddPost = new ClientRequest(URLConstant.ADD_POST);
        // accept with input type
        requestAddPost.accept("application/json");
        // input post infor
        String postInput = "{\"users_id\": 1,\"title\": \"The First Article\","
                + "\"description\": \"This is a test\"}";
        // get post infor
        requestAddPost.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<Articles> responseSuccess = requestAddPost.post(Articles.class);
        // return result add post with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();

        // ////////////////Check Fields are required/////////////
        // input post infor with miss a field title
        postInput = "{\"users_id\": 1,\"description\": \"This is a test 2\"}";
        // get data
        requestAddPost.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<Articles> responseFields = requestAddPost.post(Articles.class);
        // return add post with code 1001 is faild because user not input
        // enought post infor!
        assertEquals(responseFields.getStatus(), 1001);
        // close environment
        responseFields.close();
    }
    
    
    /**
     * Test method add change status
     * 
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testSetActive() throws Exception
    {
        // /////////////////Check change status of a post success/////////////////
        // create a request change status of a post from user
        ClientRequest request = new ClientRequest(URLConstant.ACTIVE_POST);
        // accept with input type
        request.accept("application/json");
        // input infor to change
        String input = "{\"users_id\": 1,\"id\": 1,\"status\": 1}";
        // get get infor
        request.body("application/json", input);
        // run operation and get respond
        ClientResponse<Articles> responseSuccess = request.put(Articles.class);
        // return result with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }
    
}
