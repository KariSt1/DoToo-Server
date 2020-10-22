package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user) {
        return "LoginPage";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(User user) {
        return "SignupPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(@Valid User user, BindingResult result, HttpSession session) {
        if(result.hasErrors()) {

            System.out.println("Result errors: " + result.getFieldErrors());
            return "LoginPage";
        }
        User exists = userService.login(user);
        if(exists != null){
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        }
        return "redirect:/login?error=true";
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedinGET(HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if(sessionUser  != null){
            model.addAttribute("loggedinuser", sessionUser);
            return "LoggedInUser";
        }
        return "redirect:/";
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signupPOST(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "LoginPage";
        }
        User exists = userService.findByUserName(user.username);
        if(exists != null) {
            return "redirect:/signup?error=true";
        } else {
            System.out.println("User not present, signing up now!");
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/";
        }
    }
}
