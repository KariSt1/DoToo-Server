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

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "LoginPage";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "SignupPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {

            System.out.println("Result errors: " + result.getFieldErrors());
            return "LoginPage";
        }
        Optional<User> loggedInUser = userService.findById(user.getUsername());
        if(loggedInUser.isPresent()) {
            System.out.println("User is present!");
            if(loggedInUser.get().getPassword().equals(user.getPassword())) {
                return "redirect:/";
            } else {
                return "redirect:/login?error=true";
            }
        } else {
            System.out.println("User is not present!");
            return "redirect:/login?error=true";
        }
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signupPOST(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "LoginPage";
        }
        Optional<User> loggedInUser = userService.findById(user.getUsername());
        if(loggedInUser.isPresent()) {
            return "redirect:/signup?error=true";
        } else {
            System.out.println("User not present, signing up now!");
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/";
        }
    }
}
