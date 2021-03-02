package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public User loginPOST(@Valid @RequestBody User user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
        User exists = userService.login(user);
        if (exists != null) {
            session.setAttribute("loggedInUser", exists);
            return exists;
        } {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public User signupPOST(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
        User exists = userService.findByUserName(user.username);
        if (exists == null) {
            return userService.save(user);
        } {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username unavailable");
        }
    }

    // TODO: Skoða hvort þurfi
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public String signout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/login";
    }
}
