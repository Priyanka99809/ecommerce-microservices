package com.example.userservice.service;

import com.example.userservice.model.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserService {
    private List<User> users= new ArrayList<>();
    private Long idCounter = 100L;

    public List<User> getUsers()
    {
        return users;
    }
    public String loginUser(User user)
    {
        return "Login Successful for user: "+ user.getEmail();
    }
    public User createUser(User user)
    {   user.setId(idCounter++);
        users.add(user);
        return user;
    }
}
