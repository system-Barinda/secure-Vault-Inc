package com.tumba.secure.Vault.Inc.service;




import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tumba.secure.Vault.Inc.model.User;
import com.tumba.secure.Vault.Inc.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}