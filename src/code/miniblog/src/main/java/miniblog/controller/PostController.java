package miniblog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import miniblog.constant.CommonConstant;
import miniblog.entity.Articles;
import miniblog.service.interfaces.IArticleService;
import miniblog.service.interfaces.IUserService;
import miniblog.util.ResultResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {

    // Create Article Service
    @Autowired
    @Qualifier("ArticleServiceImpl")
    private IArticleService articleService;
    
    // Create User Service
    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;

    // GET ALL POST
    @RequestMapping(value = "/post.list")
    public String getAllPosts(HttpServletRequest request, ModelMap model)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultResponse result = new ResultResponse();
        // check user login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultResponse("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        // create a list to hold data return
        List<Articles> list = new ArrayList<Articles>();
        // get data to add list
        list = articleService.list();
        model.addAttribute("postList", list);
        // return
        return "home";
    }

    // GET ALL POSTS BY USER
    @RequestMapping(value = "/post.user")
    public String getAllPostsByUser(HttpServletRequest request, ModelMap model)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultResponse result = new ResultResponse();
        // get user id
        String user_id = request.getParameter("id");
        // check user login or not and match with current user login
        if (session.getAttribute("user_id") == null || !session.getAttribute("user_id").toString().equals(user_id))
        {
            result = new ResultResponse("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }

        // create a list to hold data return
        List<Articles> list = new ArrayList<Articles>();
        
        // check user id input not null
        if (user_id != null)
            // get data to add list
            list = articleService.getAllPostByUser(Integer.valueOf(user_id));
        // if list empty
        if (list.isEmpty() || list == null)
        {
            result = new ResultResponse("You never post any article before!", null, CommonConstant.MESS_SUCCESS);
            model.addAttribute("messager", result.toString());
            return "home";
        }
        // return result success
        model.addAttribute("postList", list);
        return "home";
    }

    // ACCESS TO ADD POST PAGE
    @RequestMapping(value = "/postadd", method = RequestMethod.GET)
    public String addPost()
    {
        return "postadd";
    }

    // ADD POST
    @RequestMapping(value = "/postadd", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("article") Articles articles, ModelMap model, HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultResponse result = new ResultResponse();
        // check user login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultResponse("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        if (articles.getTitle() == null || !articles.getTitle().matches(".*\\w.*") || articles.getTitle().isEmpty())
        {
            result = new ResultResponse("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "postadd";
        }
        // create a post
        @SuppressWarnings("unused")
        boolean flat = false;
        try
        {
            // user post a article
            //get user post
            articles.setUsers_id(userService.getById(Integer.valueOf(session.getAttribute("user_id").toString())));
            //add post
            flat = articleService.add(articles);
            // return successful result !
            result = new ResultResponse("Post was created successful!", "home", CommonConstant.DIALOG_SUCCESS);
            model.addAttribute("messager", result.toString());
            return "postadd";
        } catch (Exception e)
        {   
            //if add faild
            flat = false;
            result = new ResultResponse("Post faild!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "postadd";
        }
        
    }
    //
    // // CHANGE STATUS OF POST
    // @Path("/status")
    // @PUT
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response setActive(Articles article)
    // {
    // Articles post = article;
    // articleService.setActive(post);
    // System.out.println(post.getId());
    // System.out.println(articleService.setActive(post));
    // // set status
    // StatusResponse status = new StatusResponse(200,
    // "Active/Deactive Post successfully",
    // "Active/Deactive Post success!");
    // // set result return
    // Articles re = articleService.getById(post.getId());
    // ResultResponse result = new ResultResponse(status, re);
    // return Response.status(200).entity(result).build();
    // }
    //
    // // EDIT POST
    // @Path("/edit")
    // @PUT
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response editPost(Articles article)
    // {
    // // Check fileds required
    // if (article.getTitle() == null || article.getDescription() == null ||
    // article.getId() == 0
    // || article.getDescription().isEmpty() || article.getTitle().isEmpty()
    // || !article.getTitle().matches(".*\\w.*") ||
    // !article.getDescription().matches(".*\\w.*"))
    // {
    // // set status return
    // StatusResponse status = new StatusResponse(1001,
    // "Input validation failed.", "Filed is required.");
    // // set result return
    // ResultResponse result = new ResultResponse(status, null);
    // return Response.status(1001).entity(result).build();
    // }
    // // create post
    // Articles post = new Articles();
    // // get current infor of post
    // post = articleService.getById(article.getId());
    // // set new infor
    // post.setDescription(article.getDescription());
    // post.setTitle(article.getTitle());
    // Date dayEdit = new Date();
    // post.setDate_modify(dayEdit);
    // // update post
    // articleService.update(post);
    // // set status
    // StatusResponse status = new StatusResponse(200, "Edit Post successful",
    // "Edit success!");
    // // set result return
    // ResultResponse result = new ResultResponse(status, post);
    // return Response.status(200).entity(result).build();
    // }
    //
    // // DELETE POST
    // @Path("/delete/{id}")
    // @DELETE
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response deletePost(@PathParam("id") int id)
    // {
    // if (id == 0)
    // {
    // // set status return
    // StatusResponse status = new StatusResponse(1001,
    // "Input validation failed.", "Filed is required.");
    // // set result return
    // ResultResponse result = new ResultResponse(status, null);
    // return Response.status(1001).entity(result).build();
    // }
    // // delete
    // articleService.delete(id);
    // // set status
    // StatusResponse status = new StatusResponse(200,
    // "Delete post successfully", "Delete success!");
    // // set result return
    // ResultResponse result = new ResultResponse(status, null);
    // // return result
    // return Response.status(200).entity(result).build();
    // }
    //
    //
    //

}
