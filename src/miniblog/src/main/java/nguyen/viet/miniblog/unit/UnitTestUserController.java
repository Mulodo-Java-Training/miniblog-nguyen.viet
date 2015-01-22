/**
 * 
 */
package nguyen.viet.miniblog.unit;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import nguyen.viet.miniblog.controller.UserController;
import nguyen.viet.miniblog.entity.Users;
import nguyen.viet.miniblog.serviceInterface.IUserService;

import org.apache.commons.logging.Log;
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
public class UnitTestUserController {
	@Autowired
	private IUserService userService;

	private UserController userController;

	@Before
	public void setUp() throws Exception {
		userController = new UserController();
	}

	@After
	public void tearDown() throws Exception {
		userController = null;
	}

	/**
	 * Test method add user 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testAddUser() throws Exception {
		// test Field required!
		Users u = new Users();
		assertEquals(userController.addUser(u).getStatus(), 1001);

		//delete user before, because after run this menthod test will have a user created
		ClientRequest request2 = new ClientRequest(
				"http://localhost:8080/miniblog/users/delete");//create a request delete from client
		request2.accept("application/json");//accept with input type
		String input2 = "{\"username\": \"nguyen.a\"}";//input data
		request2.body("application/json", input2);//get data
		ClientResponse<String> response2 = request2.delete(String.class);//run operation and get respond
		response2.close();//close environment
		
		ClientRequest request = new ClientRequest(
				"http://localhost:8080/miniblog/users/add");//create a request add user from client 
		request.accept("application/json");//accept with input type
		// test add new user
		String input = "{\"username\": \"nguyen.a\",\"password\": \"123456\","
				+ "\"lastname\": \"nguyen\",\"firstname\": \"test\","
				+ "\"email\":\"viet@yahoo.com\"}";//input data
		request.body("application/json", input);//get data
		ClientResponse<Users> response = request.post(Users.class);//run operation and get respond
		assertEquals(response.getStatus(), 200);// check add new user with code 200 is successful!
		response.close();//close environment

		// test add user again to check exist
		response = request.post(Users.class);//run operation and get respond
		assertEquals(response.getStatus(), 9002);// check add new user with code 9002 is login faild!
		response.close();
	}
	
	/**
	 * Test method user login
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testLogin() throws Exception{
		//create a request from client
		ClientRequest request = new ClientRequest(
				"http://localhost:8080/miniblog/users/login");
		request.accept(MediaType.APPLICATION_FORM_URLENCODED);//accept with input type
		String input = "username=nguyen.a&password=123456";//input data
		request.body(MediaType.APPLICATION_FORM_URLENCODED, input);//push data
		ClientResponse<String> response = request.post(String.class);//get respone to check
		// check login user success!
		assertEquals(response.getStatus(), 200);//return with code 200 is successful
		response.close();//close response environment
		
		//check login faild
		input = "username=nguyen.a&password=1234567";//set wrong password
		request.body(MediaType.APPLICATION_FORM_URLENCODED, input);//push data 
		response = request.post(String.class);//get respone to check
		assertEquals(response.getStatus(), 9001);//return with code 9001 is faild
		response.close();//close response environment
	}
	
}
