package com.example.userservice.controlller;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }
    @PostMapping("/register")
    public User createUser(@RequestBody User user)
    {
        return userService.createUser(user);
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody User user)
    {

         return userService.loginUser(user);

    }

    @GetMapping
    public List<User> getAllUsers()
    {

        return userService.getUsers();

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUsers()
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}