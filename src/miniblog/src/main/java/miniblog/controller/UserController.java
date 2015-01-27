package miniblog.controller;

import java.util.List;

import javax.ws.rs.Consumes;
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
import miniblog.serviceinterface.IUserService;
import miniblog.util.Commons;
import miniblog.util.ResultResponse;
import miniblog.util.StatusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@Path("users")
@Controller
public class UserController {
    // Create User Service
    @Autowired
    @Qualifier("iUserService")
    private IUserService userService;

    // Create Common controller
    private Commons commonController;

    // Create field to manager status login and logout
    private int loginStatus;

    public UserController() {
        this.commonController = new Commons();
        this.loginStatus = 0;
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
            // set data user login
            setLogin(user.getId());
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
    public Response logout()
    {
        // check user login or not
        if (checkLogin() > 0)
        {
            // reset login status
            loginStatus = 0;
            // return with login successful
            // set status
            StatusResponse status = new StatusResponse(200, "User logout successfully", "Logout success!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(200).entity(result).build();
        } else
        {
            // return when login faild because not login first
            // set status
            StatusResponse status = new StatusResponse(1002, "Check login first", "You are not login!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1002).entity(result).build();
        }
    }

    // Get user by id
    @Path("/infor/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfor(@PathParam("id") String id)
    {
        if (id.isEmpty() || id == null || id.matches("\\s+"))
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        int userId = Integer.valueOf(id);
        // check user login or not
        if (checkLogin() == userId)
        {
            // get data to add user
            Users user = userService.getById(Integer.valueOf(userId));
            if (user == null)
            {
                user = new Users();
            }
            // set status return
            StatusResponse status = new StatusResponse(200, "Get user information successfully.", "Get data success!");
            // set result return
            ResultResponse result = new ResultResponse(status, user);
            // return result
            return Response.status(200).entity(result).build();
        } else
        {
            // return when login faild because not login first
            // set status
            StatusResponse status = new StatusResponse(1002, "Check login first", "You are not login!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1002).entity(result).build();
        }
    }

    // update user by id
    @Path("/update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(Users u)
    {
        if (u.getFirstname() == null || u.getLastname() == null || u.getBirthday() == null || u.getEmail() == null)
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // create a user
        Users user = new Users();
        // get information of user from database with user id of client login
        user = userService.getById(u.getId());
        // change information client want
        user.setFirstname(u.getFirstname());
        user.setLastname(u.getLastname());
        user.setBirthday(u.getBirthday());
        user.setEmail(u.getEmail());
        // check user login or not and permission to update
        if (checkLogin() == u.getId())
        {
            // user update
            userService.update(user);
            // return status and user infor success
            // set status return
            StatusResponse status = new StatusResponse(200, "User updated successfully.", "updated successful!");
            // set result return
            ResultResponse result = new ResultResponse(status, user);
            return Response.status(200).entity(result).build();
        } else
        {
            setLogin(0);
            // return if not login
            // set status
            StatusResponse status = new StatusResponse(1002, "Check login first", "You are not login!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1002).entity(result).build();
        }
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
        // get information of user from database with user id of client login
        user = userService.getById(u.getId());
        // change password client want
        user.setPassword(commonController.MD5(u.getPassword()));
        // check user login or not and permission to update
        if (checkLogin() == u.getId())
        {
            // user update password
            userService.update(user);
            // Create Gson to parse object to json
            // set status return
            StatusResponse status = new StatusResponse(200, "Changed password successfully.", "Changed success!");
            // set result return
            ResultResponse result = new ResultResponse(status, user);
            return Response.status(200).entity(result).build();
        } else
        {
            setLogin(0);
            // return if not login
            // set status
            StatusResponse status = new StatusResponse(1002, "Check login first", "You are not login!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1002).entity(result).build();
        }
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
        // check user login or not
        if (checkLogin() > 0)
        {
            // check have or not user in database match name searching
            if (users != null)
            {
                // Create Gson to parse object to json
                Gson gson = new Gson();
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
        } else
        {
            // return if not login
            // set status
            StatusResponse status = new StatusResponse(1002, "Check login first", "You are not login!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1002).entity(result).build();
        }

    }

    // Check user login or not(status 0 = not, status > 0 = login)
    public int checkLogin()
    {
        return loginStatus;
    }

    // Set user login or not
    public void setLogin(int loginstt)
    {
        loginStatus = loginstt;
    }

    // Delete User
    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deleteUser(@FormParam("username") String username)
    {
        // Get user id by username and delete this user
        userService.delete(userService.getUsersByUsername(username).getId());
        return Response.status(200).entity("Delete Susscess").build();
    }
}
