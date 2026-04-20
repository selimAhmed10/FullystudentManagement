package org.example.fullystudentmanagement.service;

import org.example.fullystudentmanagement.model.User;
import org.example.fullystudentmanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    public User saveUser(User user)
    {
        if(user.getPassword() != null && !user.getPassword().startsWith("$2a$"))
        {
            user.setPassword((passwordEncoder.encode(user.getPassword())));
        }
        return userRepository.save(user);
    }



    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<List<User>> findAllusers() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }
}
