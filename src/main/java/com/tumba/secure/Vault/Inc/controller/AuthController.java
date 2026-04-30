package com.tumba.secure.Vault.Inc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tumba.secure.Vault.Inc.model.User;
import com.tumba.secure.Vault.Inc.service.UserService;

@Controller
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    // ✅ Show login page
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // ✅ Show register page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // ✅ Handle registration
    @PostMapping("/register")
    public String register(User user, Model model) {

        if (user.getUsername() == null || user.getPassword() == null) {
            model.addAttribute("error", "All fields are required");
            return "register";
        }

        service.register(user);
        return "redirect:/";
    }

    // ✅ Handle login
    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, Model model) {

        User user = service.login(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }

        // ❌ login failed
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    // ✅ Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}