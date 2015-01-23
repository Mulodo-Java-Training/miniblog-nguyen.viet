package miniblog.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import miniblog.entity.Users;
import miniblog.serviceinterface.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Path("users")
@Controller
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private IUserService userService;

    private int loginStatus = 0;

    // Register
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(Users user)
    {
        // Check fileds required
        if (user.getFirstname() == null || user.getLastname() == null || user.getEmail() == null
                || user.getUsername() == null || user.getPassword() == null)
            return Response.status(1001).entity("Fields are required!").build();
        // create user project
        Boolean flat = false;
        // get password client input
        String newpass = user.getPassword();
        // convert password to MD5 before input to database
        user.setPassword(MD5(newpass));
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
            // return successful result !
            return Response.status(200).entity("Account created success!-----" + user.toString()).build();
        else
            // return faild result!
            return Response.status(9002).entity("User exist!").build();
    }

    // Login
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password)
    {
        // Check client login with username and password
        if (username == null || password == null)
        {
            return Response.status(1001).entity("Fields are required!").build();
        }
        // Convert password to MD5 to compile with database
        String newpass = MD5(password);
        // Get User from database
        Users u = userService.getUserByIdPassword(username, newpass);
        // check get success or not
        if (u != null)
        {
            // set data user login
            setLogin(u.getId());
            // return if login success!
            return Response.status(200).entity("Login Susscess---! Get data:--" + u.toString()).build();
        } else
        {
            // return message if login faild
            return Response.status(9001).entity("Login faild!").build();
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
            return Response.status(200).entity("Logout successful!").build();
        } else
        {
            // return when login faild because not login first
            return Response.status(1002).entity("Login not yet!").build();
        }
    }

    // Get user by id
    @Path("/infor/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfor(@PathParam("id") String id)
    {
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
            // return with user infor
            return Response.status(200).entity("User infor: " + user.toString()).build();
        } else
        {
            // return if not login
            return Response.status(1002).entity("You are not login. ").build();
        }
    }

    // update user by id
    @Path("/update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(Users u)
    {
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
            // return status and message success
            return Response.status(200).entity("Successful!---" + user.toString()).build();
        } else
        {
            setLogin(0);
            // return if not login
            return Response.status(1002).entity("You are not login. ").build();
        }
    }

    // Change user password
    @Path("/changePass")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePass(Users u)
    {
        // create a user
        Users user = new Users();
        // get information of user from database with user id of client login
        user = userService.getById(u.getId());
        // change password client want
        user.setPassword(MD5(u.getPassword()));
        // check user login or not and permission to update
        if (checkLogin() == u.getId())
        {
            // user update password
            userService.update(user);
            // return status and message success
            return Response.status(200).entity("Change Password Successful!---" + user.toString()).build();
        } else
        {
            setLogin(0);
            // return if not login
            return Response.status(1002).entity("You are not login. ").build();
        }
    }

    // Search user by name
    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response searchByName(@FormParam("name") String name)
    {
        // get user have the same name with client input
        List<Users> users = userService.getUsersByName(name);
        //check user login or not
        if (checkLogin() > 0)
        {
            //check have or not user in database match name searching
            if (users != null)
            {
                //return if exist
                return Response.status(200).entity(users.toString()).build();
            } else
            {
                //return if not
                return Response.status(9001).entity("User not exit").build();
            }
        } else
            //return if not login
            return Response.status(200).entity("Need to login first").build();

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

    @Path("/delete")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(String name)
    {
        name = "nguyen.a";
        userService.delete(userService.getUsersByUsername(name).getId());
        return Response.status(200).entity("Delete Susscess").build();
    }

    // //// Two methods convet password to MD5////////
    private static String convertToHex(byte[] data)
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++)
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do
            {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String MD5(String text)
    {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        byte[] md5hash = new byte[32];
        try
        {
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

}
