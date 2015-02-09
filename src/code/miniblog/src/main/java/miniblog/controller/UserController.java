package miniblog.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import miniblog.constant.CommonConstant;
import miniblog.entity.Users;
import miniblog.service.interfaces.IUserService;
import miniblog.util.Commons;
import miniblog.util.ResultResponse;

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

    // Create Common controller
    private Commons commonController;

    // Messager
    String messager;

    public UserController() {
        this.commonController = new Commons();
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
        ResultResponse result = new ResultResponse();
        // Check fileds required
        if (user.getFirstname() == null || !user.getFirstname().matches(".*\\w.*") || user.getLastname() == null
                || !user.getLastname().matches(".*\\w.*") || user.getEmail() == null
                || !user.getEmail().matches(".*\\w.*") || user.getUsername() == null
                || !user.getUsername().matches(".*\\w.*") || user.getPassword() == null
                || !user.getPassword().matches(".*\\w.*"))
        {
            result = new ResultResponse("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "register";
        }
        // Check if user input birthday and it have true or not
        String day = request.getParameter("day");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String birthday = day + "/" + month + "/" + year;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        // accept if user not input birthday
        if (birthday.contains("null"))
        {
            user.setBirthday(null);
        }
        // accept if user input birthday
        else
        {
            // parse day
            Date date = (Date) formatter.parse(birthday);
            user.setBirthday(date);
        }
        // create user
        boolean flat = false;
        // get password client input
        String newpass = user.getPassword();
        // convert password to MD5 before input to database
        user.setPassword(commonController.MD5(newpass));
        // check add new user to database success or not(exits or not)
        try
        {
            // add user to database
            flat = userService.add(user);
        } catch (Exception e)
        {
            // if add faild
            flat = false;
        }
        // check create success or not
        if (flat == true)
        {
            // return if true
            result = new ResultResponse("Register successful!", "login", CommonConstant.DIALOG_SUCCESS);
            model.addAttribute("messager", result.toString());
            return "register";
        } else
        {
            // return if wrong
            result = new ResultResponse("Register faild because username exist!", "", CommonConstant.MESS_FAILD);
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
        ResultResponse result = new ResultResponse();
        // Check client login with username and password
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
        {
            result = new ResultResponse("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "welcome";
        }
        // Convert password to MD5 to compile with database
        String newpass = commonController.MD5(password);
        // Get User from database
        Users user = userService.getUserByIdPassword(username, newpass);
        // check get success or not
        if (user != null)
        {
            session.setAttribute("user_id", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("fullname", user.getLastname() + " " + user.getFirstname());
            // return if login success!
            return "home";
        } else
        {
            // return message if login faild
            result = new ResultResponse("Login faild! Wrong username or password", null, CommonConstant.MESS_FAILD);
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
                // get user infor
                model.addAttribute("user", userService.getById(id));
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
    {
        // create session to check
        HttpSession session = request.getSession(true);
        // check user update and user infor match
        if (!session.getAttribute("user_id").equals(u.getId()))
        {
            return "home";
        }
        // create messager to return
        ResultResponse result = new ResultResponse();
        if (u.getFirstname() == null || !u.getFirstname().matches(".*\\w.*") || u.getLastname() == null
                || !u.getLastname().matches(".*\\w.*") || u.getEmail() == null || !u.getEmail().matches(".*\\w.*"))
        {
            // return if user edit with wrong data
            result = new ResultResponse("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "useredit";
        }
        // get all user infor
        Users user = userService.getById(u.getId());
        if (user == null)
        {
            // return home if user not exist!
            return "home";
        }
        // Check if user input birthday and it have true or not
        String day = request.getParameter("day");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String birthday = year + "/" + month + "/" + day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date;
        try
        {
            date = formatter.parse(birthday);
        } catch (ParseException e)
        {
            date = null;
        }
        // set new infor for user
        user.setBirthday(date);
        user.setFirstname(u.getFirstname());
        user.setLastname(u.getLastname());
        user.setEmail(u.getEmail());
        // update user infor
        userService.update(user);
        // return result
        result = new ResultResponse("Edit your information successful!", null, CommonConstant.MESS_SUCCESS);
        model.addAttribute("messager", result.toString());
        // get new infor of user
        model.addAttribute("user", userService.getById(u.getId()));
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
                // get user infor
                model.addAttribute("user", userService.getById(id));
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
        ResultResponse result = new ResultResponse();
        // check user login and user infor match
        if (!session.getAttribute("user_id").equals(u.getId()))
        {
            return "home";
        }
        // get input from user
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");
        // Convert password to MD5 to compile with database
        String convertPass = commonController.MD5(oldPassword);
        // get user form username and old password
        Users user = userService.getUserByIdPassword(u.getUsername(), convertPass);
        if (user == null)
        {
            // return if user input wrong old password
            result = new ResultResponse("Wrong old password!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "changepass";
        }
        // check if user input only space or null data
        if (newPassword == null || !newPassword.matches(".*\\w.*"))
        {
            // return if user edit with wrong data
            result = new ResultResponse("Fields are required!", null, CommonConstant.MESS_FAILD);
            model.addAttribute("messager", result.toString());
            return "changepass";
        }
        String convertNewPass = commonController.MD5(newPassword);
        // set new pass for user
        user.setPassword(convertNewPass);

        // update new password
        userService.update(user);
        // return result
        result = new ResultResponse("Change password successful!", null, CommonConstant.MESS_SUCCESS);
        model.addAttribute("messager", result.toString());
        // get new infor of user
        model.addAttribute("user", userService.getById(u.getId()));
        return "changepass";
    }
    
    // Search user by name
    @RequestMapping(value = "/searchUser")
    public String searchByName(HttpServletRequest request, ModelMap model)
    {
        String nameSearch = request.getParameter("nameSearch");
        // get user have the same name with client input
        List<Users> users = userService.getUsersByName(nameSearch);
        // check have or not user in database match name searching
        // get new infor of user
        model.addAttribute("userList", users);
        return "home";
    }
}
