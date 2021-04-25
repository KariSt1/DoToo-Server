package is.hi.hbv501g.dotoo.DoToo.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListItemService;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TodoListController {

    private TodoListService todoListService;
    private TodoListItemService itemService;
    private UserService userService;

    @Autowired
    public TodoListController(TodoListService todoListService,
                              TodoListItemService itemService,
                              UserService userService) {
        this.todoListService = todoListService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @RequestMapping(value = "/todolist", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoList> getTodoLists(@RequestParam String username,
                                       @RequestParam String password) {
        User userInfo = new User(username, password);
        User loggedInUser = userService.login(userInfo);
        if (loggedInUser != null) {
            List<TodoList> test = todoListService.findByUser(loggedInUser);
            return todoListService.findByUser(loggedInUser);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
        }
    }

    @RequestMapping(value = "/todolist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> postTodoLists(@RequestParam String username,
                                                @RequestParam String password,
                                                @RequestParam int finishedTodoLists,
                                                @RequestBody List<TodoList> todolists) {
        User userInfo = new User(username, password);
        User loggedInUser = userService.login(userInfo);
        loggedInUser.setFinishedTodoLists(finishedTodoLists);
        if (loggedInUser != null) {
            for(TodoList list: todolists) {
                list.setUser(loggedInUser);
                for(TodoListItem item: list.getItems()) {
                    item.setTodoList(list);
                }
                Optional<TodoList> existingList = todoListService.findById((long) list.getId());
                if(existingList.isPresent()) todoListService.delete(existingList.get());
                todoListService.save(list);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping("/favoritetodolists")
    public List<TodoList> getFavoriteTodoLists(@RequestParam String username,
                                               @RequestParam String password) {
        User userInfo = new User(username, password);
        User loggedInUser = userService.login(userInfo);
        if (loggedInUser != null) {
            return todoListService.findByUserAndFavorite(loggedInUser, true);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user is signed in");
        }
    }

    @RequestMapping(value = "/deletelists", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> deleteTodoLists(@Valid @RequestBody ObjectNode objectNode) {
        JsonNode todoLists = objectNode.get("todolists");
        JsonNode username = objectNode.get("username");

        String sTodoLists = todoLists.toString();
        String[] allIDs = sTodoLists.replaceAll("[^0-9,]", "").split(",");

        for (String id: allIDs) {
            long deletedId = Long.parseLong(id);
            Optional<TodoList> list = todoListService.findById(deletedId);
            if(list.isPresent()) todoListService.delete(list.get());
        }
         return new ResponseEntity<>(HttpStatus.OK);
    }
}
