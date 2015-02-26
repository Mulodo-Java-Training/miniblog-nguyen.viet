package miniblog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import miniblog.entity.Comments;
import miniblog.service.interfaces.ICommentService;
import miniblog.util.ResultResponse;
import miniblog.util.StatusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Path("comments")
@Controller
public class CommentController {

    // Create Comment Service
    @Autowired
    @Qualifier("CommentServiceImpl")
    private ICommentService commentService;

    // ADD COMMENT
    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(Comments comment)
    {
        if (comment.getDescription() == null || !comment.getDescription().matches(".*\\w.*")
                || comment.getDescription().isEmpty())
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // user add comment
        commentService.add(comment);
        // return successful result !
        StatusResponse status = new StatusResponse(200, "Add comment successfully.", "Add  success!");
        // set result return
        ResultResponse result = new ResultResponse(status, comment);
        return Response.status(200).entity(result).build();
    }

    // EDIT COMMENT
    @Path("/edit")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editComment(Comments comment)
    {
        // Check fileds required
        if (comment.getDescription() == null || comment.getDescription().isEmpty()
                || !comment.getDescription().matches(".*\\w.*"))
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // create comment
        Comments cmt = new Comments();
        // get current infor of comment edit
        cmt = commentService.getById(comment.getId());
        // set new infor
        cmt.setDescription(comment.getDescription());
        Date dayEdit = new Date();
        cmt.setDate_modify(dayEdit);
        // update comment
        commentService.update(cmt);
        // set status
        StatusResponse status = new StatusResponse(200, "Edit comment successful", "Edit success!");
        // set result return
        ResultResponse result = new ResultResponse(status, cmt);
        return Response.status(200).entity(result).build();
    }

    // DELETE COMMENT
    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("id") int id)
    {
        if (id == 0)
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // delete
        commentService.delete(id);
        // set status
        StatusResponse status = new StatusResponse(200, "Delete comment successfully", "Delete success!");
        // set result return
        ResultResponse result = new ResultResponse(status, null);
        // return result
        return Response.status(200).entity(result).build();
    }

    // GET ALL COMMENT BY USER
    @Path("/user/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByUser(@PathParam("id") int userID)
    {
        // create a list to hold data return
        List<Comments> list = new ArrayList<Comments>();
        // get data to add list
        list = commentService.listByUser(userID);
        // set status
        StatusResponse status = new StatusResponse(200, "Get All comment by user successfully.", "Get success!");
        // set result return
        ResultResponse result = new ResultResponse(status, list);
        // return result
        return Response.status(200).entity(result).build();
    }

    // GET ALL COMMENT BY POST
    @Path("/post/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByPost(@PathParam("id") int articleID)
    {
        // create a list to hold data return
        List<Comments> list = new ArrayList<Comments>();
        // get data to add list
        list = commentService.listByPost(articleID);
        // set status
        StatusResponse status = new StatusResponse(200, "Get All comment by post successfully.", "Get success!");
        // set result return
        ResultResponse result = new ResultResponse(status, list);
        // return result
        return Response.status(200).entity(result).build();
    }
    
    // GET COMMENT BY ID
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostById(@PathParam("id") int id)
    {
        // create a list to hold data return
        Comments comment = new Comments();
        // get data to add list
        comment = commentService.getById(id);
        if(comment != null){
            // set status
            StatusResponse status = new StatusResponse(200, "Get comment successfully.", "Get comment success!");
            // set result return
            ResultResponse result = new ResultResponse(status, comment);
            // return result
            return Response.status(200).entity(result).build();
        }else
        {
            // set status
            StatusResponse status = new StatusResponse(9001, "Comment not exist!.", "Not exist!");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            // return result
            return Response.status(9001).entity(result).build();
        }
    
    }
}
