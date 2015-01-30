package miniblog.unittest;

import static org.junit.Assert.assertEquals;
import miniblog.constant.URLConstant;
import miniblog.entity.Articles;
import miniblog.util.ResultResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

/**
 * @author NguyenLeQuoc
 *
 */
@SuppressWarnings("deprecation")
public class PostControllerUnitTest {

    @Before
    public void setUp() throws Exception
    {
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
        /* CHECK ADD POST SUCCESS */
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

        /* GET ID OF POST JUST ADD TO DELETE AFTER */
        // Create json object
        Gson gson = new Gson();
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responseSuccess.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Article
        Articles post = new ObjectMapper().readValue(gson.toJson(result.getData()), Articles.class);
        // Get id of post add
        int articleID = post.getId();
        // close environment
        responseSuccess.close();

        /* CHECK FIELDS REQUIRED */
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

        /* DELETE POST JUST ADD */
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_POST + articleID);
        // run operation and get respond
        requestDelete.delete(ResultResponse.class);
    }

    /**
     * Test method add change status
     * 
     * @throws Exception
     */
    @Test
    public void testSetActive() throws Exception
    {
        /* CHECK CHANGE STATUS */
        // create a request add post from user to test change status
        ClientRequest requestAddPost = new ClientRequest(URLConstant.ADD_POST);
        // accept with input type
        requestAddPost.accept("application/json");
        // input post infor
        String postInput = "{\"users_id\": 1,\"title\": \"Test add Article\"," + "\"description\": \"This is a test\"}";
        // get post infor
        requestAddPost.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<ResultResponse> responseAdd = requestAddPost.post(ResultResponse.class);
        // return result add post with code 200 is successful!
        assertEquals(responseAdd.getStatus(), 200);

        /* GET ID OF POST JUST ADD TO DELETE AFTER */
        // Create json object
        Gson gson = new Gson();
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responseAdd.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Article
        Articles post = new ObjectMapper().readValue(gson.toJson(result.getData()), Articles.class);
        // Get id of post add
        int articleID = post.getId();
        // close environment
        responseAdd.close();

        // create a request change status of a post from user
        ClientRequest requestChange = new ClientRequest(URLConstant.ACTIVE_POST);
        // accept with input type
        requestChange.accept("application/json");
        // input infor to change
        String input = "{\"id\": " + articleID + ",\"status\": 0}";
        // get get infor
        requestChange.body("application/json", input);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = requestChange.put(ResultResponse.class);
        // return result with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();

        /* DELETE POST JUST ADD EDIT */
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_POST + articleID);
        // run operation and get respond
        requestDelete.delete(ResultResponse.class);
    }

    /**
     * Test method add edit post
     * 
     * @throws Exception
     */
    @Test
    public void testEditPost() throws Exception
    {
        /* Check edit a post */
        // create a request add post from user to test change status
        ClientRequest requestAddPost = new ClientRequest(URLConstant.ADD_POST);
        // accept with input type
        requestAddPost.accept("application/json");
        // input post infor
        String postInput = "{\"users_id\": 1,\"title\": \"Test add Article\"," + "\"description\": \"This is a test\"}";
        // get post infor
        requestAddPost.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<ResultResponse> responseAdd = requestAddPost.post(ResultResponse.class);
        // return result add post with code 200 is successful!
        assertEquals(responseAdd.getStatus(), 200);

        /* GET ID OF POST JUST ADD TO DELETE AFTER */
        // Create json object
        Gson gson = new Gson();
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responseAdd.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Article
        Articles post = new ObjectMapper().readValue(gson.toJson(result.getData()), Articles.class);
        // Get id of post add
        int articleID = post.getId();
        // close environment
        responseAdd.close();

        // create a request edit a post from user
        ClientRequest request = new ClientRequest(URLConstant.EDIT_POST);
        // accept with input type
        request.accept("application/json");
        // input infor to change
        String postNew = "{\"id\": " + articleID
                + ",\"title\": \"The First Article\",\"description\": \"This is a test edit\"}";
        // get get infor
        request.body("application/json", postNew);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.put(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();

        /* Check edit with fields required */
        postInput = "{\"id\": 1,\"title\": \"  \",\"description\": \"This is a test edit\"}";
        request.body("application/json", postInput);
        responseSuccess = request.put(ResultResponse.class);
        // return result with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 1001);
        // close environment
        responseSuccess.close();

        /* DELETE POST JUST ADD EDIT */
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_POST + articleID);
        // run operation and get respond
        requestDelete.delete(ResultResponse.class);
    }

    /**
     * Test method add delete post
     * 
     * @throws Exception
     */
    @Test
    public void testDeletePost() throws Exception
    {
        /*CHECK DELETE POST*/
        // create a request add post from user to try delete
        ClientRequest requestAddPost = new ClientRequest(URLConstant.ADD_POST);
        // accept with input type
        requestAddPost.accept("application/json");
        // input post infor
        String postInput = "{\"users_id\": 1,\"title\": \"Test add Article\","
                + "\"description\": \"This is a test to delete\"}";
        // get post infor
        requestAddPost.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = requestAddPost.post(ResultResponse.class);

        /* Get Id of post to delete */
        // Create json object
        Gson gson = new Gson();
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responseSuccess.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Article
        Articles post = new ObjectMapper().readValue(gson.toJson(result.getData()), Articles.class);
        // Get id of post add
        int postID = post.getId();
        // create a request delete of a post from user
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_POST + postID);
        // run operation and get respond
        ClientResponse<ResultResponse> responseDelete = requestDelete.delete(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseDelete.getStatus(), 200);
        // close environment
        responseDelete.close();

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
