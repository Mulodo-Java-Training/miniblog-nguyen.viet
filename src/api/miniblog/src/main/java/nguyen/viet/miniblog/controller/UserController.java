package nguyen.viet.miniblog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nguyen.viet.miniblog.entity.Users;
import nguyen.viet.miniblog.serviceImpl.UserServiceImpl;
import nguyen.viet.miniblog.serviceInterface.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Path("users")
@Controller
public class UserController {

	@Autowired
	private IUserService userService;

	public void setEmployeeManager(IUserService employeeManager) {
		this.userService = employeeManager;
	}

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

	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(Users user) {
		Boolean check = false;
		try{
		   check = userService.add(user);
		}catch(Exception e){
		   check = false;
		}
		if(check == true & user != null)
		 return Response.status(200).entity("Account created success!-----" + user.toString()).build();
		else
		 return Response.status(9002).entity("User exist!").build();
	}
}
