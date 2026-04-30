package com.tumba.secure.Vault.Inc.controller;



import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tumba.secure.Vault.Inc.model.Secret;
import com.tumba.secure.Vault.Inc.model.User;
import com.tumba.secure.Vault.Inc.service.SecretService;

@Controller
public class SecretController {

    private final SecretService service;

    public SecretController(SecretService service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) throws Exception {
        User user = (User) session.getAttribute("user");
        model.addAttribute("secrets", service.getUserSecrets(user));
        return "dashboard";
    }

    @PostMapping("/secret")
    public String addSecret(Secret secret, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        secret.setUser(user);
        service.save(secret);
        return "redirect:/dashboard";
    }
}