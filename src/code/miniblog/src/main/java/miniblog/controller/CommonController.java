package miniblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() 
    {
        return "welcome";
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request) 
    {
        HttpSession session = request.getSession();
        if (session.getAttribute("user_id") != null)
            return "home";
        else
            return "login";
    }
}
