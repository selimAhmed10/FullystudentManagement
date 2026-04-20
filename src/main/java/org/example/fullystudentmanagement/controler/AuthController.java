package org.example.fullystudentmanagement.controler;

import org.example.fullystudentmanagement.model.Role;
import org.example.fullystudentmanagement.model.User;
import org.example.fullystudentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    private UserService userService;

    public  AuthController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user)
    {
        user.setRole(Role.STUDENT);
        userService.registerUser(user);
        return ResponseEntity.ok("user registered successfully");
    }
}
