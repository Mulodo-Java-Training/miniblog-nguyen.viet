package miniblog.test.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import miniblog.constant.URLConstant;
import miniblog.entity.Articles;
import miniblog.entity.Users;
import miniblog.util.ResultResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author NguyenLeQuoc
 *
 */
@SuppressWarnings("deprecation")
public class PostControllerUnitTest {

    //create User object to test
    private Users users;
    
    //create Article object to test
    private Articles post;
    
    //create Gson object to parse Java Object to Json
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    
    @Before
    public void setUp() throws Exception
    {
        /*ADD USER FOR OPERATE WITH POST */
        // create a request add user from client
        ClientRequest requestAddUser = new ClientRequest(URLConstant.CREATE_USER);
        // accept with input type
        requestAddUser.accept(MediaType.APPLICATION_JSON);
        // input data
        Users u = new Users();
        u.setUsername("nguyen.viet.test.post");
        u.setPassword("123456");
        u.setFirstname("nguyen le quoc");
        u.setLastname("viet");
        u.setEmail("unittest@gmail.com");
        // set data
        requestAddUser.body(MediaType.APPLICATION_JSON, gson.toJson(u));
        // run operation and get respond
        ClientResponse<ResultResponse> response = requestAddUser.post(ResultResponse.class);
        //Parse result return to ResultResponse object
        ResultResponse resultUser = gson.fromJson(response.getEntity(String.class), ResultResponse.class);
        //Parse Data object(json format) of ResultResponse to Users
        Users user = new ObjectMapper().readValue(gson.toJson(resultUser.getData()), Users.class);
        //Get id of user just add
        users = user;
        // close environment
        response.close();
        
        /*ADD POST TO TEST*/
        // create a request add post from user
        ClientRequest requestAddPost = new ClientRequest(URLConstant.ADD_POST);
        // accept with input type
        requestAddPost.accept(MediaType.APPLICATION_JSON);
        // input post infor
        Articles article = new Articles();
        article.setTitle("Unit Post");
        article.setDescription("Test Post");
        article.setUsers_id(users.getId());
        // get post infor
        requestAddPost.body(MediaType.APPLICATION_JSON, gson.toJson(article));
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = requestAddPost.post(ResultResponse.class);
        /* GET ID OF POST JUST ADD TO DELETE AFTER */
        // Parse result return to ResultResponse object
        ResultResponse resultPost = gson.fromJson(responseSuccess.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Article
        Articles post = new ObjectMapper().readValue(gson.toJson(resultPost.getData()), Articles.class);
        this.post = post;
        // close environment
        responseSuccess.close();
    }

    @After
    public void tearDown() throws Exception
    {
        /*create a request delete a user account of adding test*/
        ClientRequest userDelete = new ClientRequest(URLConstant.DELETE_USER + this.users.getId());
        // run operation and get respond
        userDelete.delete(ResultResponse.class);
        
        /*create a request delete a post of adding test*/
        ClientRequest postDelete = new ClientRequest(URLConstant.DELETE_POST + this.post.getId());
        // run operation and get respond
        postDelete.delete(ResultResponse.class);
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
        requestAddPost.accept(MediaType.APPLICATION_JSON);
        // input post infor
        Articles article = new Articles();
        article.setTitle("Unit Test Post");
        article.setDescription("Test Add Post");
        article.setUsers_id(users.getId());
        // get post infor
        requestAddPost.body(MediaType.APPLICATION_JSON, gson.toJson(article));
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = requestAddPost.post(ResultResponse.class);
        // return result add post with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 200);

        /* GET ID OF POST JUST ADD TO DELETE AFTER */
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
        article.setTitle(" ");
        // get data
        requestAddPost.body(MediaType.APPLICATION_JSON, gson.toJson(article));
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
        // create a request change status of a post from user
        ClientRequest requestChange = new ClientRequest(URLConstant.ACTIVE_POST);
        // accept with input type
        requestChange.accept(MediaType.APPLICATION_JSON);
        // change status
        this.post.setStatus(1);
        // get get infor
        requestChange.body(MediaType.APPLICATION_JSON, gson.toJson(this.post));
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = requestChange.put(ResultResponse.class);
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
        /* Check edit a post */
        // create a request edit a post from user
        ClientRequest request = new ClientRequest(URLConstant.EDIT_POST);
        // accept with input type
        request.accept(MediaType.APPLICATION_JSON);
        // input new description to change
        this.post.setDescription("Test Edit Post");
        // get get infor
        request.body(MediaType.APPLICATION_JSON, gson.toJson(this.post));
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.put(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();

        /* Check edit with fields required */
        this.post.setTitle("");
        request.body(MediaType.APPLICATION_JSON, gson.toJson(this.post));
        responseSuccess = request.put(ResultResponse.class);
        // return result with code 1001 is field required!
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
        /*CHECK DELETE POST*/
        // create a request add post from user to try delete
        ClientRequest requestAddPost = new ClientRequest(URLConstant.ADD_POST);
        // accept with input type
        requestAddPost.accept(MediaType.APPLICATION_JSON);
        // input post infor
        Articles article = new Articles();
        article.setTitle("Unit Post Delete");
        article.setDescription("Test Post Delete");
        article.setUsers_id(users.getId());
        // get post infor
        requestAddPost.body(MediaType.APPLICATION_JSON, gson.toJson(article));
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = requestAddPost.post(ResultResponse.class);

        /* Get Id of post to delete */
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
        // create a request get posts by user id
        ClientRequest request = new ClientRequest(URLConstant.USER_POST + this.users.getId());
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.get(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }
}
