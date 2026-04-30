package com.tumba.secure.Vault.Inc.controller;



import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.tumba.secure.Vault.Inc.model.User;
import com.tumba.secure.Vault.Inc.service.UserService;

@Controller
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(User user) {
        service.register(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {
        User user = service.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}