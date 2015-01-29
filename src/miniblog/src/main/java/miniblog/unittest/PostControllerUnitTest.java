package miniblog.unittest;

import static org.junit.Assert.assertEquals;
import miniblog.constant.URLConstant;
import miniblog.controller.PostController;
import miniblog.util.ResultResponse;

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
public class PostControllerUnitTest {
    @Autowired
    private PostController postController;

    @Before
    public void setUp() throws Exception
    {
        postController = new PostController();
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
    @Test
    public void testAddPost() throws Exception
    {

        // /////////////////Check create a post success/////////////////
        // create a request add post from user
        ClientRequest requestAddPost = new ClientRequest(URLConstant.ADD_POST);
        // accept with input type
        requestAddPost.accept("application/json");
        // input post infor
        String postInput = "{\"users_id\": 1,\"title\": \"Test add Article\"," + "\"description\": \"This is a test\"}";
        // get post infor
        requestAddPost.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = requestAddPost.post(ResultResponse.class);
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
        ClientResponse<ResultResponse> responseFields = requestAddPost.post(ResultResponse.class);
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
    @Test
    public void testSetActive() throws Exception
    {
        // /////////////////Check change status of a post
        // success/////////////////
        // create a request change status of a post from user
        ClientRequest request = new ClientRequest(URLConstant.ACTIVE_POST);
        // accept with input type
        request.accept("application/json");
        // input infor to change
        String input = "{\"users_id\": 1,\"id\": 1,\"status\": 1}";
        // get get infor
        request.body("application/json", input);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.put(ResultResponse.class);
        // return result with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }

    /**
     * Test method add edit post
     * 
     * @throws Exception
     */
    @Test
    public void testEditPost() throws Exception
    {
        // /////////////////Check edit a post /////////////////
        // create a request edit a post from user
        ClientRequest request = new ClientRequest(URLConstant.EDIT_POST);
        // accept with input type
        request.accept("application/json");
        // input infor to change
        String postInput = "{\"id\": 1,\"title\": \"The First Article\",\"description\": \"This is a test edit\"}";
        // get get infor
        request.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.put(ResultResponse.class);

        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();

        // //////Check edit with fields required//////////
        postInput = "{\"id\": 1,\"title\": \"  \",\"description\": \"This is a test edit\"}";
        request.body("application/json", postInput);
        responseSuccess = request.put(ResultResponse.class);
        // return result with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 1001);
        // close environment
        responseSuccess.close();
    }

    /**
     * Test method add delete post
     * 
     * @throws Exception
     */
    @Test
    public void testDeletePost() throws Exception
    {
        // /////////////////Check delete a post /////////////////
        // set example postID = 2
        int postID = 2;
        // create a request change status of a post from user
        ClientRequest request = new ClientRequest(URLConstant.DELETE_POST + postID);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.delete(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();

    }

    /**
     * Test method get all Posts
     * 
     * @throws Exception
     */
    @Test
    public void testGetListPost() throws Exception
    {
        // create a request all posts from user
        ClientRequest request = new ClientRequest(URLConstant.LIST_POST);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.get(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }

    /**
     * Test method get all Posts by user
     * 
     * @throws Exception
     */
    @Test
    public void testGetAllPostsByUser() throws Exception
    {
        // set user id to test
        int userID = 1;
        // create a request get posts by user id
        ClientRequest request = new ClientRequest(URLConstant.USER_POST + userID);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.get(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }
}
