package miniblog.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import miniblog.api.CommonAPI;
import miniblog.constant.CommonConstant;
import miniblog.entity.Articles;
import miniblog.entity.Users;
import miniblog.service.interfaces.IArticleService;
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
public class UserController {
    // Create User Service
    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;

    // Create Article Service
    @Autowired
    @Qualifier("ArticleServiceImpl")
    private IArticleService articleService;

    // Create API operation
    CommonAPI api;

    // Messager
    String messager;

    public UserController() {
        this.api = new CommonAPI();
    }

    // access Register page
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goRegisterPage(ModelMap map)
    {
        return "register";
    }

    // do Register
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") Users user, ModelMap model, HttpServletRequest request)
            throws ParseException
    {
        ResultReturn result = new ResultReturn();
        // Check fileds required
        if (user.getFirstname() == null || !user.getFirstname().matches(".*\\w.*") || user.getLastname() == null
                || !user.getLastname().matches(".*\\w.*") || user.getEmail() == null
                || !user.getEmail().matches(".*\\w.*") || user.getUsername() == null
                || !user.getUsername().matches(".*\\w.*") || user.getPassword() == null
                || !user.getPassword().matches(".*\\w.*"))
        {
            result = new ResultReturn("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "register";
        }
        // Check if user input birthday and it have true or not
        String day = request.getParameter("day");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String birthday = year + "-" + month + "-" + day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // accept if user not input birthday
        if (birthday.contains("null"))
        {
            user.setBirthday(null);
        }
        // accept if user input birthday
        else
        {
            Date date = new Date();
            date = formatter.parse(birthday);
            user.setBirthday(date);
        }
        ResultResponse apiReturn = userService.add(user);
        // check success or not
        if (apiReturn.getMeta().getCode() == 200)
        {
            // return if true
            result = new ResultReturn(apiReturn.getMeta().getMessage(), "login", CommonConstant.DIALOG_SUCCESS);
            model.addAttribute("messager", result.toString());
            return "register";
        } else
        {
            // return if wrong
            result = new ResultReturn(apiReturn.getMeta().getMessage(), "", CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "register";
        }
    }

    // access Login page
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "login";
    }

    // do login
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            ModelMap model, HttpServletRequest request)
    {
        // create session to save user infor
        HttpSession session = request.getSession(true);
        ResultReturn result = new ResultReturn();
        // Get User from database
        ResultResponse apiReturn = userService.getUserByIdPassword(username, password);
        // check get success or not
        if (apiReturn.getMeta().getCode() == 200)
        {
            Users user = (Users) api.parseData(apiReturn, CommonConstant.TYPE_USER);
            // store user infor
            session.setAttribute("user_id", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("fullname", user.getLastname() + " " + user.getFirstname());
            // return if login success!
            // create a list to hold data return
            apiReturn = articleService.list();
            // parse data to add list
            @SuppressWarnings("unchecked")
            List<Articles> list = (List<Articles>) api.parseData(apiReturn, CommonConstant.TYPE_POST_LIST);
            model.addAttribute("postList", list);
            return "home";
        } else
        {
            // return message if login faild
            result = new ResultReturn(apiReturn.getMeta().getMessage(), null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "login";
        }
    }

    // Logout
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        // remove all session
        session.invalidate();
        return "welcome";
    }

    // access update User
    @RequestMapping(value = "/useredit", method = RequestMethod.GET)
    public String editUser(@RequestParam(value = "id", required = false) int id, HttpServletRequest request,
            ModelMap model)
    {
        // create session to get current user login
        HttpSession session = request.getSession();
        // check user login or not
        if (session.getAttribute("user_id") != null)
        {
            // check user infor match with current user login
            if (session.getAttribute("user_id").equals(id))
            {
                ResultResponse apiReturn = userService.getById(id);
                Users user = (Users) api.parseData(apiReturn, CommonConstant.TYPE_USER);
                // get user infor
                model.addAttribute("user", user);
                return "useredit";
            } else
            {
                // return home if no match
                return "home";
            }
        } else
            // return if user not login
            return "login";
    }

    // update user
    @RequestMapping(value = "/useredit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") Users u, ModelMap model, HttpServletRequest request)
            throws ParseException
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // check user update and user infor match
        if (!session.getAttribute("user_id").equals(u.getId()))
        {
            result = new ResultReturn("You are not alow", null, CommonConstant.MESS_SUCCESS);
            model.addAttribute("messager", result.toString());
            return "home";
        }
        // get all user infor default
        ResultResponse apiReturn = userService.getById(u.getId());
        Users userDefault = (Users) api.parseData(apiReturn, CommonConstant.TYPE_USER);
        // Check if user input birthday and it have true or not
        String day = request.getParameter("day");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String birthday = year + "-" + month + "-" + day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // accept if user not input birthday
        if (birthday.contains("null"))
        {
            u.setBirthday(null);
        }
        // accept if user input birthday
        else
        {
            Date date = new Date();
            date = formatter.parse(birthday);
            u.setBirthday(date);
        }
        // update user infor
        ResultResponse apiUpdateReturn = userService.update(u);
        Users userUpdate = (Users) api.parseData(apiUpdateReturn, CommonConstant.TYPE_USER);
        // return result
        if (userUpdate == null)
        {
            // return home if user not exist!
            result = new ResultReturn(apiUpdateReturn.getMeta().getMessage(), null, CommonConstant.MESS_SUCCESS);
            model.addAttribute("messager", result.toString());
            model.addAttribute("user", userDefault);
            return "useredit";
        }
        result = new ResultReturn(apiUpdateReturn.getMeta().getMessage(), null, CommonConstant.MESS_SUCCESS);
        model.addAttribute("messager", result.toString());
        // get new infor of user
        model.addAttribute("user", userUpdate);
        return "useredit";
    }

    // access user password
    @RequestMapping(value = "/changepass", method = RequestMethod.GET)
    public String changeUserPass(@RequestParam(value = "id", required = false) int id, HttpServletRequest request,
            ModelMap model)
    {
        // create session to get current user login
        HttpSession session = request.getSession();
        // check user login or not
        if (session.getAttribute("user_id") != null)
        {
            // check user infor match with current user login
            if (session.getAttribute("user_id").equals(id))
            {
                // get infor user default
                ResultResponse apiReturn = userService.getById(id);
                Users userDefault = (Users) api.parseData(apiReturn, CommonConstant.TYPE_USER);
                model.addAttribute("user", userDefault);
                return "changepass";
            } else
            {
                // return home if no match
                return "home";
            }
        } else
            // return if user not login
            return "login";
    }

    // change user password
    @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    public String changeUserPass(@ModelAttribute("user") Users u, ModelMap model, HttpServletRequest request)
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // create messager to return
        ResultReturn result = new ResultReturn();
        // check user login and user infor match
        if (!session.getAttribute("user_id").equals(u.getId()))
        {
            result = new ResultReturn("You need login first", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "home";
        }
        // get input from user
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");
        // get user form username and old password
        ResultResponse apiReturn = userService.getUserByIdPassword(u.getUsername(), oldPassword);
        Users user = (Users) api.parseData(apiReturn, CommonConstant.TYPE_USER);
        if (user == null)
        {
            // return if user input wrong old password
            result = new ResultReturn("Wrong old password!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "changepass";
        }
        // check if user input only space or null data
        if (newPassword == null || !newPassword.matches(".*\\w.*"))
        {
            // return if user edit with wrong data
            result = new ResultReturn("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "changepass";
        }
        // set new pass for user
        user.setPassword(newPassword);
        System.out.println(user.getPassword());
        // update new password
        ResultResponse apiChangepass = userService.changePassword(user);
        // return result
        result = new ResultReturn(apiChangepass.getMeta().getMessage(), null, CommonConstant.MESS_SUCCESS);
        model.addAttribute("messager", result.toString());
        // get new infor of user
        Users userNew = (Users) api.parseData(apiChangepass, CommonConstant.TYPE_USER);
        model.addAttribute("user", userNew);
        return "changepass";
    }

    // Search user by name
    @RequestMapping(value = "/searchUser")
    public String searchByName(HttpServletRequest request, ModelMap model)
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
        // get input from user
        String nameInput = request.getParameter("nameSearch");
        // cut off space in string
        String nameSearch = nameInput.trim();
        // check if user input only space or empty field
        if (nameSearch.isEmpty() || !nameSearch.matches(".*\\w.*"))
        {
            return "home";
        }
        // get user have the same name with client input
        ResultResponse apiReturn = userService.getUsersByName(nameInput);
        @SuppressWarnings("unchecked")
        List<Users> users = (List<Users>) api.parseData(apiReturn, CommonConstant.TYPE_USER_LIST);
        // check have or not user in database match name searching
        if (users.isEmpty() || users == null)
        {
            result = new ResultReturn("Not have any exist!", null, CommonConstant.MESS_SUCCESS);
        }
        model.addAttribute("messager", result.toString());
        // get new infor of user
        model.addAttribute("userList", users);
        return "home";
    }
}
