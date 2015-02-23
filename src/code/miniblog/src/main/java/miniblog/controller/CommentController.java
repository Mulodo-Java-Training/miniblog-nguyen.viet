package miniblog.controller;

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
public class CommentController {

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

    public CommentController() {
        this.api = new CommonAPI();
    }

    // ADD COMMENT
    @RequestMapping(value = "/comment.add", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("comment") Comments comment, ModelMap model, HttpServletRequest request)
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
        if (comment.getDescription() == null || !comment.getDescription().matches(".*\\w.*")
                || comment.getDescription().isEmpty())
        {
            result = new ResultReturn("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "postdetail";
        }
        ResultResponse apiReturn;
        comment.setUsers_id(Integer.valueOf(session.getAttribute("user_id").toString()));
        // user add comment
        apiReturn = commentService.add(comment);
        // return successful result !

        // get list return from api
        apiReturn = commentService.listByPost(comment.getArticles_id());
        // parse data to add list
        @SuppressWarnings("unchecked")
        List<Comments> list = (List<Comments>) api.parseData(apiReturn, CommonConstant.TYPE_COMMENT_LIST);
        model.addAttribute("commentList", list);

        // get post return
        apiReturn = articleService.getById(comment.getArticles_id());
        Articles post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
        model.addAttribute("post", post);
        return "postdetail";
    }

     // EDIT COMMENT
    @RequestMapping(value = "/comment.edit", method = RequestMethod.POST)
    public String editComment(@ModelAttribute("comment") Comments comment, ModelMap model, HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // Get current post
        ResultResponse apiReturn = commentService.getById(comment.getId());
        Comments currentComment = (Comments) api.parseData(apiReturn, CommonConstant.TYPE_COMMENT);
        // check login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        // if comment not exist
        if (currentComment == null)
        {
            apiReturn = articleService.list();
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
            model.addAttribute("postList", list);
            return "home";
        }
        // check user update and user comment match
        if (!session.getAttribute("user_id").equals(currentComment.getUsers_id()))
        {
            result = new ResultReturn("You are not alow!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            model.addAttribute("post", currentComment);
            return "postedit";
        }
        // create post
        Comments cmt = new Comments();
        // get current infor of post
        cmt = currentComment;
        // set new infor
        cmt.setDescription(comment.getDescription());
        // update post
        apiReturn = commentService.update(cmt);
        if (apiReturn.getMeta().getCode() == 200)
        {
            result = new ResultReturn(apiReturn.getMeta().getMessage(), null, CommonConstant.MESS_SUCCESS);
            model.addAttribute("messager", result.toString());
        } else
        {
            result = new ResultReturn(apiReturn.getMeta().getMessage(), null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
        }
        // get list return from api
        apiReturn = commentService.listByPost(cmt.getArticles_id());
        // parse data to add list
        @SuppressWarnings("unchecked")
        List<Comments> list = (List<Comments>) api.parseData(apiReturn, CommonConstant.TYPE_COMMENT_LIST);
        model.addAttribute("commentList", list);

        // get post return
        apiReturn = articleService.getById(cmt.getArticles_id());
        Articles post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
        model.addAttribute("post", post);
        return "postdetail";
    }

    // DELETE COMMENT
    @RequestMapping(value = "/comment.delete", method = RequestMethod.GET)
    public String deleteComment(@RequestParam(value = "id", required = false) int id, ModelMap model,
            HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // create post
        Comments cmt = new Comments();
        // get current infor of post
        ResultResponse apiReturn = commentService.getById(id);
        cmt = (Comments) api.parseData(apiReturn, CommonConstant.TYPE_COMMENT);
        // check login or not
        if (session.getAttribute("user_id") == null)
        {
            result = new ResultReturn("You need login first!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }

        // check comment exist or not
        if (cmt == null)
        {
            result = new ResultReturn("Comment Not exist", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            apiReturn = articleService.list();
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
            model.addAttribute("postList", list);
            return "home";
        }
        //permission with current user
        if (!session.getAttribute("user_id").equals(cmt.getUsers_id()))
        {
            result = new ResultReturn("You are not alow!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            // get list return from api
            apiReturn = commentService.listByPost(cmt.getArticles_id());
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Comments> list = (List<Comments>) api.parseData(apiReturn, CommonConstant.TYPE_COMMENT_LIST);
            model.addAttribute("commentList", list);
            // get post return
            apiReturn = articleService.getById(cmt.getArticles_id());
            Articles post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
            model.addAttribute("post", post);
            return "postdetail";
        }

        // delete
        apiReturn = commentService.delete(id);
        result = new ResultReturn(apiReturn.getMeta().getMessage(), null, CommonConstant.MESS_SUCCESS);
        model.addAttribute("messager", result.toString());
        
        // get list comment return 
        apiReturn = commentService.listByPost(cmt.getArticles_id());
        // parse data to add list
        @SuppressWarnings("unchecked")
        List<Comments> list = (List<Comments>) api.parseData(apiReturn, CommonConstant.TYPE_COMMENT_LIST);
        model.addAttribute("commentList", list);

        // get post return
        apiReturn = articleService.getById(cmt.getArticles_id());
        Articles post = (Articles) api.parseData(apiReturn, CommonConstant.TYPE_POST);
        model.addAttribute("post", post);
        
        return "postdetail";
    }
}
