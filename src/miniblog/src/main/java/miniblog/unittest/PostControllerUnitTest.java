package miniblog.unittest;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import miniblog.constant.URLConstant;
import miniblog.controller.PostController;
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
    // Create object Usercontroller to test
    @Autowired
    private PostController postController;

    // Create object CommonController to support test
    @Before
    public void setUp() throws Exception
    {
        postController = new PostController();
    }

    @After
    public void tearDown() throws Exception
    {
        postController = null;
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
        // Format date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        // get current date time with Date()
        Date date = new Date();
        // set date_create a post
        String date_create = dateFormat.format(date).toString();

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

        // ///////////////////Check login first////////////////////
        postInput = "{\"title\": \"The First Article\"," + "\"description\": \"This is a test\"}";
        // get data
        requestAddPost.body("application/json", postInput);
        // run operation and get respond
        ClientResponse<Articles> responseLogin = requestAddPost.post(Articles.class);
        // return add post with code 1002 is faild because user not login!
        assertEquals(responseLogin.getStatus(), 1002);
        // close environment
        responseLogin.close();

        // ////////////////Check Fields are required/////////////
        // input post infor with miss a field title
        postInput = "{\"users_id\": 1,\"description\": \"This is a test\"}";
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
}
