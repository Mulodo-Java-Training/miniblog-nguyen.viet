package miniblog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import miniblog.api.CommonAPI;
import miniblog.constant.CommonConstant;
import miniblog.entity.Articles;
import miniblog.service.interfaces.IArticleService;
import miniblog.util.ResultResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    CommonAPI api;

    public CommonController() {
        this.api = new CommonAPI();
    }
    
    // Create Article Service
    @Autowired
    @Qualifier("ArticleServiceImpl")
    private IArticleService articleService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() 
    {
            return "welcome";
    }
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(HttpServletRequest request, ModelMap model) 
    {
        HttpSession session = request.getSession();
        if (session.getAttribute("user_id") != null){
            //get return from api
            ResultResponse apiReturn = articleService.list();
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
            model.addAttribute("postList", list);
            return "home";
            }
        else
            return "welcome";
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request, ModelMap model) 
    {
        HttpSession session = request.getSession();
        if (session.getAttribute("user_id") != null){
            //get return from api
            ResultResponse apiReturn = articleService.list();
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
            model.addAttribute("postList", list);
            return "home";
            }
        else
            return "login";
    }
}
