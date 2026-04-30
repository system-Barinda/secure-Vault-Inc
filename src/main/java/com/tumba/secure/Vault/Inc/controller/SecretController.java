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

    // ✅ Dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        return "dashboard";
    }

    // ✅ Secrets page
    @GetMapping("/secrets")
    public String secretsPage(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("secrets", service.getUserSecrets(user));

        String lastPlatform = (String) session.getAttribute("lastPlatform");
        model.addAttribute("lastPlatform", lastPlatform);

        return "secrets";
    }

    // ✅ Add secret
    @PostMapping("/secret")
    public String addSecret(@ModelAttribute Secret secret,
                            HttpSession session,
                            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (secret.getTitle() == null || secret.getTitle().isEmpty()
                || secret.getContent() == null || secret.getContent().length() < 4) {

            model.addAttribute("error", "Platform and secret must be valid (min 4 chars)");
            model.addAttribute("secrets", service.getUserSecrets(user));
            return "secrets";
        }

        secret.setUser(user);
        service.save(secret);

        session.setAttribute("lastPlatform", secret.getTitle());

        return "redirect:/secrets";
    }

    // ✅ Delete (FIX: redirect to login)
    @PostMapping("/secret/delete/{id}")
    public String deleteSecret(@PathVariable Long id, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        service.delete(id);

        return "redirect:/secrets";
    }

    // ✅ Show edit page
    @GetMapping("/secret/edit/{id}")
    public String editSecret(@PathVariable Long id, Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Secret secret = service.findById(id);

        model.addAttribute("secret", secret);

        return "edit-secret";
    }

    // ✅ FIXED: UPDATE METHOD (THIS WAS MISSING 🔥)
    @PostMapping("/secret/update")
    public String updateSecret(@ModelAttribute Secret secret,
                               HttpSession session,
                               Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        // validation
        if (secret.getTitle() == null || secret.getTitle().isEmpty()
                || secret.getContent() == null || secret.getContent().length() < 4) {

            model.addAttribute("error", "Invalid update data");
            model.addAttribute("secret", secret);
            return "edit-secret";
        }

        // get existing
        Secret existing = service.findById(secret.getId());

        // update values
        existing.setTitle(secret.getTitle());
        existing.setContent(secret.getContent());

        service.save(existing);

        return "redirect:/secrets";
    }
}