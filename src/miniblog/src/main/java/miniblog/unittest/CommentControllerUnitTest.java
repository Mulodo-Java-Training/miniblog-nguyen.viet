package miniblog.unittest;

import static org.junit.Assert.assertEquals;
import miniblog.constant.URLConstant;
import miniblog.entity.Comments;
import miniblog.util.ResultResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class CommentControllerUnitTest {

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
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
        request.accept("application/json");
        // input comment infor
        String input = "{\"users_id\": 1,\"articles_id\": 1," + "\"description\": \"This is a test\"}";
        // get comment infor
        request.body("application/json", input);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.post(ResultResponse.class);
        // return result add comment with code 200 is successful!
        assertEquals(responseSuccess.getStatus(), 200);

        /* GET ID OF COMMENT JUST ADD TO DELETE*/
        Gson gson = new Gson();
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
        input = "{\"users_id\": 1,\"articles_id\": 1," + "\"description\":\"\"}";
        // get data
        request.body("application/json", input);
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
        // create a comment to edit first
        // create a request add comment from user
        ClientRequest request = new ClientRequest(URLConstant.ADD_COMMENT);
        // accept with input type
        request.accept("application/json");
        // input comment infor
        String input = "{\"users_id\": 1,\"articles_id\": 1," + "\"description\": \"This is a test\"}";
        // get comment infor
        request.body("application/json", input);
        // run operation and get respond
        ClientResponse<ResultResponse> responeCreate = request.post(ResultResponse.class);
        /* GET ID OF COMMENT JUST ADD TO DELETE AFTER*/
        Gson gson = new Gson();
        // Parse result return to ResultResponse object
        ResultResponse result = gson.fromJson(responeCreate.getEntity(String.class), ResultResponse.class);
        // Parse Data object(json format) of ResultResponse to Comment
        Comments comment = new ObjectMapper().readValue(gson.toJson(result.getData()), Comments.class);
        // Get id of user just add
        int commentID = comment.getId();
        // close environment
        responeCreate.close();
        
        // create a request edit a comment from user
        ClientRequest requestEdit = new ClientRequest(URLConstant.EDIT_COMMENT);
        // accept with input type
        requestEdit.accept("application/json");
        // input infor to change
        String inputNew = "{\"id\": "+commentID+",\"description\": \"This is a test edit comment\"}";
        // get infor
        requestEdit.body("application/json", inputNew);
        // run operation and get respond
        ClientResponse<ResultResponse> response = requestEdit.put(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(response.getStatus(), 200);
        // close environment
        response.close();

        /* CHECK EDIT WITH FIELDS REQUIRED */
        input = "{\"id\": 1,\"description\":\"\"}";
        requestEdit.body("application/json", input);
        response = requestEdit.put(ResultResponse.class);
        // return result with code 200 is successful!
        assertEquals(response.getStatus(), 1001);
        // close environment
        response.close();
        
        /* DELETE COMMENT JUST ADD TO EDIT*/
        ClientRequest requestDelete = new ClientRequest(URLConstant.DELETE_COMMENT + commentID);
        // run operation and get respond
        requestDelete.delete(ResultResponse.class);
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
        request.accept("application/json");
        // input comment infor
        String input = "{\"users_id\": 1,\"articles_id\": 1," + "\"description\": \"This is a test delete\"}";
        // get comment infor
        request.body("application/json", input);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.post(ResultResponse.class);
        /* Get Id of comment to delete */
        Gson gson = new Gson();
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
        // set user id to test
        int userID = 1;
        // create a request get comment by user id
        ClientRequest request = new ClientRequest(URLConstant.USER_COMMENT + userID);
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
        // set user id to test
        int postID = 1;
        // create a request get comment by user id
        ClientRequest request = new ClientRequest(URLConstant.POST_COMMENT + postID);
        // run operation and get respond
        ClientResponse<ResultResponse> responseSuccess = request.get(ResultResponse.class);
        // /// return result with code 200 is successful!//////
        assertEquals(responseSuccess.getStatus(), 200);
        // close environment
        responseSuccess.close();
    }
}
