package miniblog.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import miniblog.serviceinterface.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Path("/")
@Controller
public class CommonController {

    @Autowired
    private IUserService userService;

    // Delete User
    @Path("deleteUser")
    @POST
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deleteUser(@FormParam("username") String username)
    {
        //Get user id by username and delete this user
        userService.delete(userService.getUsersByUsername(username).getId());
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

    public String MD5(String text)
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
