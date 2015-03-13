package miniblog.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import miniblog.entity.Users;
import miniblog.service.IUserService;
import miniblog.util.Commons;
import miniblog.util.ResultResponse;
import miniblog.util.StatusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Path("users")
@Controller
public class UserController {
    // Create User Service
    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;

    // Create Common controller
    private Commons commonController;

    public UserController() {
        this.commonController = new Commons();
    }

    // Register
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(Users user)
    {
        // Check fileds required
        if (user.getFirstname() == null || user.getLastname() == null || user.getEmail() == null
                || user.getUsername() == null || user.getPassword() == null)
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // create user project
        Boolean flat = false;
        // get password client input
        String newpass = user.getPassword();
        // convert password to MD5 before input to database
        user.setPassword(commonController.MD5(newpass));
        // check add new user to database success or not(exits or not)
        try
        {
            // add user to database
            flat = userService.add(user);
        } catch (Exception e)
        {
            // if add faild
            flat = false;
        }
        // check create success or not
        if (flat == true & user != null)
        {
            // set status return
            StatusResponse status = new StatusResponse(200, "User account was created successfully!",
                    "Account created!");
            // set result return
            ResultResponse result = new ResultResponse(status, user);
            // return successful result !
            return Response.status(200).entity(result).build();
        } else
        {
            // return faild result!
            // set status return
            StatusResponse status = new StatusResponse(9002, "User account was created faild!", "Account exist!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(9002).entity(result).build();
        }
    }

    // Login
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password)
    {
        
        // Check client login with username and password
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // Convert password to MD5 to compile with database
        String newpass = commonController.MD5(password);
        // Get User from database
        Users user = userService.getUserByIdPassword(username, newpass);
        // check get success or not
        if (user != null)
        {
            // return if login success!
            // set status return
            StatusResponse status = new StatusResponse(200, "User account was login successfully", "Login success!");
            // set result return
            ResultResponse result = new ResultResponse(status, user);
            return Response.status(200).entity(result).build();
        } else
        {
            // return message if login faild
            StatusResponse status = new StatusResponse(9001, "Data not exist", "Login Faild!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(9001).entity(result).build();
        }
    }

    // Logout
    @Path("/logout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout()
    {
        // set status
        StatusResponse status = new StatusResponse(200, "User logout successfully", "Logout success!");
        // set result return
        ResultResponse result = new ResultResponse(status, null);
        return Response.status(200).entity(result).build();
    }

    // Get user by id
    @Path("/infor/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfor(@PathParam("id") int id)
    {

        // get data to add user
        Users user = userService.getById(id);
        if (user == null)
        {
            // set status return
            StatusResponse status = new StatusResponse(200, "User not exist.", "Get data faild!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            // return result
            return Response.status(9001).entity(result).build();
            
        }
        // set status return
        StatusResponse status = new StatusResponse(200, "Get user information successfully.", "Get data success!");
        // set result return
        ResultResponse result = new ResultResponse(status, user);
        // return result
        return Response.status(200).entity(result).build();
    }

    // update user by id
    @Path("/update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(Users u)
    {
        if (u.getFirstname() == null || !u.getFirstname().matches(".*\\w.*") || u.getLastname() == null
             || !u.getLastname().matches(".*\\w.*") || u.getEmail() == null || !u.getEmail().matches(".*\\w.*"))
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // create a user
        Users user = new Users();
        // get information of user from database with user id
        user = userService.getById(u.getId());
        // change information client want
        user.setFirstname(u.getFirstname());
        user.setLastname(u.getLastname());
        user.setBirthday(u.getBirthday());
        user.setEmail(u.getEmail());
        // user update
        userService.update(user);
        // return status and user infor success
        // set status return
        StatusResponse status = new StatusResponse(200, "User updated successfully.", "updated successful!");
        // set result return
        ResultResponse result = new ResultResponse(status, user);
        return Response.status(200).entity(result).build();
    }

    // Change user password
    @Path("/changePass")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePass(Users u)
    {
        if (u.getPassword() == null)
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // create a user
        Users user = new Users();
        // get information of user from database with user id
        user = userService.getById(u.getId());
        // change password client want
        user.setPassword(commonController.MD5(u.getPassword()));
        // user update password
        userService.update(user);
        // Create Gson to parse object to json
        // set status return
        StatusResponse status = new StatusResponse(200, "Changed password successfully.", "Changed success!");
        // set result return
        ResultResponse result = new ResultResponse(status, user);
        return Response.status(200).entity(result).build();
    }

    // Search user by name
    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response searchByName(@FormParam("name") String name)
    {
        // get user have the same name with client input
        List<Users> users = userService.getUsersByName(name);
        // check have or not user in database match name searching
        if (users != null)
        {
            // return if exist
            StatusResponse status = new StatusResponse(200, "Search user information successfully.",
                    "Get data success!");
            // set result return
            ResultResponse result = new ResultResponse(status, users);
            return Response.status(200).entity(result).build();
        } else
        {
            // return if not
            // set status
            StatusResponse status = new StatusResponse(9001, "Data not exist", "Search Faild!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(9001).entity(result).build();
        }

    }

    // Delete User
    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id)
    {
        // delete user
        userService.delete(id);
        //set status
        StatusResponse status = new StatusResponse(200, "Delete user successfully", "Delete success!");
        // set result return
        ResultResponse result = new ResultResponse(status, null);
        // return result
        return Response.status(200).entity(result).build();
    }
}
