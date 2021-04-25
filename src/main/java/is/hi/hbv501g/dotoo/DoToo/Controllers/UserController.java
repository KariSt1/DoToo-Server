package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public User loginPOST(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
        User exists = userService.login(user);
        if (exists != null) {
            return exists;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public User signupPOST(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Signup info error");
        }
        User exists = userService.findByUserName(user.username);
        if (exists == null) {
            return userService.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username unavailable");
        }
    }

    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addFriend(@RequestParam String username,
                                @RequestParam String password,
                                @RequestBody JSONObject friendJSON) {
        String friendUsername = friendJSON.getAsString("friendUsername");
        User userInfo = new User(username, password);
        User loggedInUser = userService.login(userInfo);
        JSONObject json = new JSONObject();
        if (loggedInUser != null) {
            User friend = userService.findByUserName(friendUsername);
            if (friend == null) {
                json.put("error", "No user exists with that username.");
            } else {
                if (friend.getUsername() == loggedInUser.getUsername()) {
                    json.put("error", "You cannot add yourself as a friend.");
                } else {
                    if (!loggedInUser.getFriends().contains(friendUsername)) {
                        loggedInUser.addFriend(friendUsername);
                        userService.save(loggedInUser);
                        json.put("friendName", friend.getName());
                        json.put("friendUsername", friend.getUsername());
                        json.put("streak", friend.getFinishedTodoLists());
                    } else {
                        json.put("error", friend.getName() + " is already your friend.");
                    }
                }
            }
        } else {
            json.put("error", "User authentication error.");
        }
        return json;
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getFriends(@RequestParam String username,
                                       @RequestParam String password) {
        User userInfo = new User(username, password);
        User loggedInUser = userService.login(userInfo);
        JSONArray friendsList = new JSONArray();
        if (loggedInUser != null) {
            List<String> friends = loggedInUser.getFriends();
            for(String friend: friends) {
                JSONObject friendJson = new JSONObject();
                User friendUser = userService.findByUserName(friend);
                friendJson.put("username", friendUser.getUsername());
                friendJson.put("name", friendUser.getName());
                friendJson.put("highestStreak", friendUser.getFinishedTodoLists());
                friendsList.add(friendJson);
            }
        }

        return friendsList;
    }
}
