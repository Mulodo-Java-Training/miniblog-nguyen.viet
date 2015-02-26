package miniblog.test.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import miniblog.constant.URLConstant;
import miniblog.entity.Articles;
import miniblog.entity.Comments;
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

@SuppressWarnings("deprecation")
public class CommentControllerUnitTest {
    
    //create User object to test
    private Users users;
    
    //create Article object to test
    private Articles post;
    
    //create Comment object to test
    private Comments comment;
    
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
        u.setUsername("test.comment");
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
        /* GET USER JUST ADD*/
        this.users = user;
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
        /* GET POST JUST ADD*/
        // Parse result return to ResultResponse object
        ResultResponse resultPost = gson.fromJson(responseSuccess.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Article
        Articles post = new ObjectMapper().readValue(gson.toJson(resultPost.getData()), Articles.class);
        this.post = post;
        // close environment
        responseSuccess.close();
        
        /*ADD COMMENT TO TEST*/
        // create a request add comment from user
        ClientRequest request = new ClientRequest(URLConstant.ADD_COMMENT);
        // accept with input type
        request.accept(MediaType.APPLICATION_JSON);
        // input comment infor
        Comments cmt = new Comments();
        cmt.setUsers_id(this.users.getId());
        cmt.setArticles_id(this.post.getId());
        cmt.setDescription("Add Comment");
        // get comment infor
        request.body(MediaType.APPLICATION_JSON, gson.toJson(cmt));
        // run operation and get respond
        ClientResponse<ResultResponse> responseCmt = request.post(ResultResponse.class);
        /* GET COMMENT JUST ADD*/
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responseCmt.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Comment
        Comments comment = new ObjectMapper().readValue(gson.toJson(result.getData()), Comments.class);
        // Get id of user just add
        this.comment = comment;
        // close environment
        responseCmt.close();
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
        
        /*create a request delete a comment of adding test*/
        ClientRequest commentDelete = new ClientRequest(URLConstant.DELETE_COMMENT + this.comment.getId());
        // run operation and get respond
        commentDelete.delete(ResultResponse.class);
    }

    /**
     * Test method add comment
     * 
     * @throws Exception
     */
    @Test
    public void testAddComment() throws Exception
    {
        /*CHECK CREATE COMMENT*/
        // create a request add comment from user
        ClientRequest request = new ClientRequest(URLConstant.ADD_COMMENT);
        // accept with input type
        request.accept(MediaType.APPLICATION_JSON);
        // input comment infor
        Comments cmt = new Comments();
        cmt.setUsers_id(this.users.getId());
        cmt.setArticles_id(this.post.getId());
        cmt.setDescription("Test Add Comment");
        // get comment infor
        request.body(MediaType.APPLICATION_JSON, gson.toJson(cmt));
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.post(ResultResponse.class);
        // return result add comment with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 200);

        /* GET ID OF COMMENT JUST ADD TO DELETE*/
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responseSuccess.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Comment
        Comments comment = new ObjectMapper().readValue(gson.toJson(result.getData()), Comments.class);
        // Get id of user just add
        int commentID = comment.getId();
        // close environment
        responseSuccess.close();

        /*CHECK FIELDS REQUIRED*/
        // input comment infor with miss a field description
        cmt.setDescription(" ");
        // get comment infor
        request.body(MediaType.APPLICATION_JSON, gson.toJson(cmt));
        // run operation and get respond
        ClientResponse<ResultResponse> responseFields = request.post(ResultResponse.class);
        // return add comment with code 1001 is faild because user not input
        // enought comment infor!
        assertEquals(responseFields.getStatus(), 1001);
        // close environment
        responseFields.close();

        /* DELETE COMMENT JUST ADD */
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_COMMENT + commentID);
        // run operation and get respond
        requestDelete.delete(ResultResponse.class);
    }
    
    /**
     * Test method add edit comment
     * 
     * @throws Exception
     */
    @Test
    public void testEditComment() throws Exception
    {
        /* CHECK EDIT COMMENT */
        // create a request edit a comment from user
        ClientRequest requestEdit = new ClientRequest(URLConstant.EDIT_COMMENT);
        // accept with input type
        requestEdit.accept(MediaType.APPLICATION_JSON);
        // change comment infor
        this.comment.setDescription("Test Edit Comment");
        // get infor
        requestEdit.body(MediaType.APPLICATION_JSON, gson.toJson(this.comment));
        // run operation and get respond
        ClientResponse<ResultResponse> response = requestEdit.put(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(response.getStatus(), 200);
        // close environment
        response.close();

        /* CHECK EDIT WITH FIELDS REQUIRED */
        this.comment.setDescription(" ");
        requestEdit.body(MediaType.APPLICATION_JSON, gson.toJson(this.comment));
        response = requestEdit.put(ResultResponse.class);
        // return result with code 200 is successful!
        assertEquals(response.getStatus(), 1001);
        // close environment
        response.close();
    }
    
    /**
     * Test method add delete comment
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteComment() throws Exception
    {
        /*CHECK DELETE COMMENT*/
        // create a request add comment try to delete
        ClientRequest request = new ClientRequest(URLConstant.ADD_COMMENT);
        // accept with input type
        request.accept(MediaType.APPLICATION_JSON);
        // input comment infor
        Comments cmt = new Comments();
        cmt.setUsers_id(this.users.getId());
        cmt.setArticles_id(this.post.getId());
        cmt.setDescription("Test Delete Comment");
        // get comment infor
        request.body(MediaType.APPLICATION_JSON, gson.toJson(cmt));
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.post(ResultResponse.class);
        /* Get Id of comment to delete */
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responseSuccess.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Comment
        Comments comment = new ObjectMapper().readValue(gson.toJson(result.getData()), Comments.class);
        // Get id of user just add
        int commentID = comment.getId();
        
        /*create a request delete of a comment from user*/
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_COMMENT + commentID);
        // run operation and get respond
        ClientResponse<ResultResponse> responseDelete = requestDelete.delete(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseDelete.getStatus(), 200);
        // close environment
        responseDelete.close();
    }
    
    /**
     * Test method get all comment by user
     * 
     * @throws Exception
     */
    @Test
    public void testGetByUser() throws Exception
    {
        // create a request get comment by user id
        ClientRequest request = new ClientRequest(URLConstant.USER_COMMENT + this.users.getId());
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.get(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }
    
    /**
     * Test method get all comment by post
     * 
     * @throws Exception
     */
    @Test
    public void testGetByPost() throws Exception
    {
        // create a request get comment by user id
        ClientRequest request = new ClientRequest(URLConstant.POST_COMMENT + this.post.getId());
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.get(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }
}
