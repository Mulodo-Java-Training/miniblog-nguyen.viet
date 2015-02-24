package miniblog.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import miniblog.api.CommonAPI;
import miniblog.constant.CommonConstant;
import miniblog.entity.Articles;
import miniblog.entity.Comments;
import miniblog.service.interfaces.IArticleService;
import miniblog.service.interfaces.ICommentService;
import miniblog.service.interfaces.IUserService;
import miniblog.util.ResultResponse;
import miniblog.util.ResultReturn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    // Create Comment Service
    @Autowired
    @Qualifier("CommentServiceImpl")
    private ICommentService commentService;
    
    // Create Article Service
    @Autowired
    @Qualifier("ArticleServiceImpl")
    private IArticleService articleService;

    // Create User Service
    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;

    // Create API operation
    CommonAPI api;

    // Messager
    String messager;

    public PostController() {
        this.api = new CommonAPI();
    }

    // GET ALL POST
    @RequestMapping(value = "/post.list")
    public String getAllPosts(HttpServletRequest request, ModelMap model)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // check user login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        // get return from api
        ResultResponse apiReturn = articleService.list();
        // parse data to add list
        @SuppressWarnings("unchecked")
        List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
        model.addAttribute("postList", list);
        // return
        return "home";
    }

    // GET ALL POSTS BY USER
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/post.user")
    public String getAllPostsByUser(HttpServletRequest request, ModelMap model)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // get user id
        String user_id = request.getParameter("id");
        // check user login or not and match with current user login
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }

        // create a list to hold data return
        List<Articles> list = new ArrayList<Articles>();

        // check user id input not null
        if (user_id != null)
        {
            ResultResponse apiReturn = articleService.getAllPostByUser(Integer.valueOf(user_id));
            // parse data to add list
            list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
        }
        // if list empty
        if (list.isEmpty() || list == null)
        {
            result = new ResultReturn("You never post any article before!", null, CommonConstant.MESS_SUCCESS);
            model.addAttribute("messager", result.toString());
            return "home";
        }
        // return result success
        model.addAttribute("postList", list);
        return "home";
    }

    // ACCESS TO ADD POST PAGE
    @RequestMapping(value = "/post.add", method = RequestMethod.GET)
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
        ResultReturn result = new ResultReturn();
        // check user login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        // create a post
        articles.setUsers_id(Integer.valueOf(session.getAttribute("user_id").toString()));
        ResultResponse apiReturn = articleService.add(articles);
        if (apiReturn.getMeta().getCode() == 200)
        {
            // return if true
            Articles post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
            result = new ResultReturn(apiReturn.getMeta().getMessage(), "post.detail?id="+post.getId(), CommonConstant.DIALOG_SUCCESS);
            model.addAttribute("messager", result.toString());
            return "postadd";
        } else
        {
            // return if wrong
            result = new ResultReturn(apiReturn.getMeta().getMessage(), "", CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "postadd";
        }
    }

    // ACCESS TO POST DETAIL
    @RequestMapping(value = "/post.detail", method = RequestMethod.GET)
    public String postDetail(@RequestParam(value = "id", required = false) int id, ModelMap model,
            HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // check user login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        //return post infor
        ResultResponse apiReturn = articleService.getById(id);
        Articles post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
        model.addAttribute("post", post);
        
        // return list comment of this post
        apiReturn = commentService.listByPost(post.getId());
        // parse data to add list
        @SuppressWarnings("unchecked")
        List<Comments> list = (List<Comments>) api.parseData(apiReturn, CommonConstant.TYPE_COMMENT_LIST);
        model.addAttribute("commentList", list);
        return "postdetail";
    }

    // ACCESS TO EDIT POST
    @RequestMapping(value = "/post.edit", method = RequestMethod.GET)
    public String editPost(@RequestParam(value = "id", required = false) int id, ModelMap model,
            HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        ResultResponse apiReturn = articleService.getById(id);
        Articles post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
        // Permission access
        if (post != null && post.getUsers_id() == Integer.valueOf(session.getAttribute("user_id").toString()))
        {
            // set post return
            model.addAttribute("post", post);
            return "postedit";
        } else
        {
            apiReturn = articleService.list();
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
            model.addAttribute("postList", list);
            return "home";
        }
    }

    // EDIT POST
    @RequestMapping(value = "/post.edit", method = RequestMethod.POST)
    public String editPost(@ModelAttribute("article") Articles article, ModelMap model, HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // Get current post
        ResultResponse apiReturn = articleService.getById(article.getId());
        Articles currentPost = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
        // check login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        // if post not exist
        if (currentPost == null)
        {
            apiReturn = articleService.list();
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
            model.addAttribute("postList", list);
            return "home";
        }
        // check user update and user post match
        if (!session.getAttribute("user_id").equals(currentPost.getUsers_id()))
        {
            result = new ResultReturn("You are not alow!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            model.addAttribute("post", currentPost);
            return "postedit";
        }
        // create post
        Articles post = new Articles();
        // get current infor of post
        post = currentPost;
        // set new infor
        post.setDescription(article.getDescription());
        post.setTitle(article.getTitle());
        // update post
        apiReturn = articleService.update(post);
        if (apiReturn.getMeta().getCode() == 200)
        {
            result = new ResultReturn(apiReturn.getMeta().getMessage(), null, CommonConstant.MESS_SUCCESS);
            model.addAttribute("messager", result.toString());
            model.addAttribute("post", post);
        } else
        {
            result = new ResultReturn(apiReturn.getMeta().getMessage(), null, CommonConstant.MESS_SUCCESS);
            model.addAttribute("messager", result.toString());
            model.addAttribute("post", currentPost);
        }
        return "postdetail";
    }

    // DELETE POST
    @RequestMapping(value = "/post.delete", method = RequestMethod.GET)
    public String deletePost(@RequestParam(value = "id", required = false) int id, ModelMap model,
            HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // create post
        Articles post = new Articles();
        // get current infor of post
        ResultResponse apiReturn = articleService.getById(id);
        post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
        // check login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        
        // check user update and user post match
        if (post != null)
        {
             if(!session.getAttribute("user_id").equals(post.getUsers_id()))
             {
                 result = new ResultReturn("You are not alow!", null,CommonConstant.MESS_FAILD);
                 model.addAttribute("messager", result.toString());
                 // get posts to add list
                 apiReturn = articleService.getAllPostByUser(Integer.valueOf(session.getAttribute("user_id").toString()));
                 // parse data to add list
                 @SuppressWarnings("unchecked")
                 List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
                 // set list post of user to return
                 model.addAttribute("postList", list);
                 return "home";
             }
        }
        // delete
        articleService.delete(id);
        result = new ResultReturn("Deleted successful!", null, CommonConstant.MESS_SUCCESS);
        model.addAttribute("messager", result.toString());
        // get posts to add list
        apiReturn = articleService.getAllPostByUser(Integer.valueOf(session.getAttribute("user_id").toString()));
        // parse data to add list
        @SuppressWarnings("unchecked")
        List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
        // set list post of user to return
        model.addAttribute("postList", list);
        // set status
        return "home";
    }
    
    // LIST OF TOP POST
    @RequestMapping(value = "/post.top", method = RequestMethod.GET)
    public String getTopPosts( ModelMap model,HttpServletRequest request)
    {
     // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // check user login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        // get return from api
        ResultResponse apiReturn = articleService.list();
        // parse data to add list
        @SuppressWarnings("unchecked")
        List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
        Collections.reverse(list);
        model.addAttribute("postList", list);
        // return
        return "home";
    }
}
