package org.example.fullystudentmanagement.service;

import jakarta.validation.constraints.Null;
import org.example.fullystudentmanagement.model.User;
import org.example.fullystudentmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    public List<User> findAll()
    {
        return userRepository.findAll();
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

    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }
}
