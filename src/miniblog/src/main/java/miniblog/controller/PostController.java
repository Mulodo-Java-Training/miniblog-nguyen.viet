package miniblog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import miniblog.entity.Articles;
import miniblog.serviceinterface.IArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Path("posts")
@Controller
public class PostController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create Article Service
    @Autowired
    private IArticleService articleService;

    public PostController() {
    }

    // Post a article
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPost(Articles articles)
    {
        // Check fileds required
        if (articles.getUsers_id() == null)
            return Response.status(1002).entity("Please login first!").build();
        if (articles.getTitle() == null)
            return Response.status(1001).entity("Fields are required!").build();
        // create a post
        boolean flat = false;
        try
        {
            // user post a article
            flat = articleService.add(articles);
        } catch (Exception e)
        {
            // if post faild
            flat = false;
        }
        // return successful result !
        return Response.status(200).entity("Post successful!-----" + articles.getDate_create()).build();

    }
    
//    @Path("/status")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response setActive(Articles acticle){
//        Articles post = acticle;
//        articleService.setActive(post);
//        return Response.status(200).entity("Post successful!-----" + post.getStatus()).build();
//    }
}
