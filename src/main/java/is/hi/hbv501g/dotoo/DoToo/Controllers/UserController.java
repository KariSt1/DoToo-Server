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

    @RequestMapping("/login")
    public String LoginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "LoginPage";
    }

    @RequestMapping("/signup")
    public String SignupPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "SignupPage";
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginUser(@Valid User user, BindingResult result, Model model) {
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

    @RequestMapping(value="/signupUser", method = RequestMethod.POST)
    public String signupUser(@Valid User user, BindingResult result, Model model) {
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
