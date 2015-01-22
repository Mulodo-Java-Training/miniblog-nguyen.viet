package nguyen.viet.miniblog.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nguyen.viet.miniblog.entity.Users;
import nguyen.viet.miniblog.serviceInterface.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Path("users")
@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	@Context
	private HttpServletRequest request;

	public void setEmployeeManager(IUserService employeeManager) {
		this.userService = employeeManager;
	}

	//show all user
	@Path("/show")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response show() {
		List<Users> empData = new ArrayList<Users>();
		for (int i = 0; i < userService.list().size(); i++) {
			Users u = new Users();
			u = userService.list().get(i);
			empData.add(u);
		}
		return Response.status(200).entity(empData).build();
	}

	// Register
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(Users user) {
		// Check fileds required
		if (user.getFirstname() == null || user.getLastname() == null
				|| user.getEmail() == null || user.getUsername() == null
				|| user.getPassword() == null)
			return Response.status(1001).entity("Fields are required!").build();
		// create user
		Boolean check = false;
		String newpass = user.getPassword();//get password client input
		user.setPassword(MD5(newpass));//convert password to MD5 before input to database
		//check add new user to database success or not(exits or not)
		try {
			check = userService.add(user);//add user to database
		} catch (Exception e) {
			check = false;
		}
		// check create success or not
		if (check == true & user != null)
			return Response.status(200)
					.entity("Account created success!-----" + user.toString())
					.build();//return successful result !
		else
			return Response.status(9002).entity("User exist!").build(); //return faild result!
	}

	// Login
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("username") String username,@FormParam("password") String password) {
		//Check client login with username and password
		if (username == null || password == null) {
			return Response.status(1001).entity("Fields are required!").build();
		}
		//Convert password to MD5 to compile with database
		String newpass = MD5(password);
		Users u = userService.getUserByIdPassword(username, newpass);
		if (u != null) {
			//use Session to store user for all system
			HttpSession session = request.getSession();
			session.setAttribute("username", u.getUsername());
			session.setAttribute("userid", u.getId());
			//return if login success!
			return Response
					.status(200)
					.entity("Login Susscess---! Session get data:--"
							+ session.getAttribute("username")).build();
		} else
			//return message if login faild
			return Response.status(9001).entity("Login faild!").build();
	}
	
	

	public int checkLogin() {
		int stt = 0;
		stt = Integer.valueOf((String) request.getSession().getAttribute("userid"));
		return stt;
	}

	@Path("/delete")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(String name) {
		name = "nguyen.a";
		userService.delete(userService.getUsersByUsername(name).getId());
		return Response.status(200).entity("Delete Susscess").build();
	}

	// Two methods convet password to MD5
	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String MD5(String text) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] md5hash = new byte[32];
		try {
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md5hash = md.digest();
		return convertToHex(md5hash);
	}

}
