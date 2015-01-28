package miniblog.controller;

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
}
