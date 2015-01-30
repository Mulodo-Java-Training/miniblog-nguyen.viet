package miniblog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
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

import miniblog.entity.Articles;
import miniblog.service.interfaces.IArticleService;
import miniblog.util.ResultResponse;
import miniblog.util.StatusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Path("posts")
@Controller
public class PostController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create Article Service
    @Autowired
    @Qualifier("ArticleServiceImpl")
    private IArticleService articleService;

    public PostController() {
    }

    // Post a article
    @SuppressWarnings("unused")
    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPost(Articles articles)
    {
        if (articles.getTitle() == null)
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        // create a post
        boolean flat = false;
        try
        {
            // user post a article
            flat = articleService.add(articles);
        } catch (Exception e)
        {
            e.fillInStackTrace();
            flat = false;
        }
        // return successful result !
        StatusResponse status = new StatusResponse(200, "Post was created successful!", "Create success!");
        // set result return
        ResultResponse result = new ResultResponse(status, articles);
        return Response.status(200).entity(result).build();

    }

    // change status
    @Path("/status")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setActive(Articles article)
    {
        Articles post = article;
        articleService.setActive(post);
        // set status
        StatusResponse status = new StatusResponse(200, "Active/Deactive Post successfully",
                "Active/Deactive Post success!");
        // set result return
        Articles re = articleService.getById(post.getId());
        ResultResponse result = new ResultResponse(status, re);
        return Response.status(200).entity(result).build();
    }

    // Edit post
    @Path("/edit")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPost(Articles article)
    {
        // Check fileds required
        if (article.getTitle() == null || article.getDescription() == null || article.getId() == 0
                || article.getDescription().isEmpty() || article.getTitle().isEmpty()
                || !article.getTitle().matches(".*\\w.*") || !article.getDescription().matches(".*\\w.*"))
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        //create post
        Articles post = new Articles();
        //get current infor of post
        post = articleService.getById(article.getId());
        //set new infor
        post.setDescription(article.getDescription());
        post.setTitle(article.getTitle());
        Date dayEdit = new Date();
        post.setDate_modify(dayEdit);
        //update post
        articleService.update(post);
        // set status
        StatusResponse status = new StatusResponse(200, "Edit Post successful", "Edit success!");
        // set result return
        ResultResponse result = new ResultResponse(status, post);
        return Response.status(200).entity(result).build();
    }
    
    //delete post
    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@PathParam("id") String id)
    {
        if (id.isEmpty() || id == null || !id.matches(".*\\w.*"))
        {
            // set status return
            StatusResponse status = new StatusResponse(1001, "Input validation failed.", "Filed is required.");
            // set result return
            ResultResponse result = new ResultResponse(status, null);
            return Response.status(1001).entity(result).build();
        }
        int article_id = Integer.valueOf(id);
        // get data to add user
        articleService.delete(article_id);
        //set status
        StatusResponse status = new StatusResponse(200, "Delete post successfully", "Delete success!");
        // set result return
        ResultResponse result = new ResultResponse(status, null);
        // return result
        return Response.status(200).entity(result).build();
    }
    
    //get all post
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts()
    {
        //create a list to hold data return
        List<Articles> list = new ArrayList<Articles>();
        // get data to add list
        list = articleService.list();
        //set status
        StatusResponse status = new StatusResponse(200, "Get All Post successfully.", "Get All Post success!");
        // set result return
        ResultResponse result = new ResultResponse(status, list);
        // return result
        return Response.status(200).entity(result).build();
    }
    
    //get all post by user 
    @Path("/listby/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPostsByUser(@PathParam("id") int userID)
    {
        //create a list to hold data return
        List<Articles> list = new ArrayList<Articles>();
        // get data to add list
        list = articleService.getAllPostByUser(userID);
        //set status
        StatusResponse status = new StatusResponse(200, "Get All Post By User successfully.", "Get Post by user success!");
        // set result return
        ResultResponse result = new ResultResponse(status, list);
        // return result
        return Response.status(200).entity(result).build();
    }
}
