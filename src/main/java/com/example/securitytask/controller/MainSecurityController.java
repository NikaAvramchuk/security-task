package com.example.securitytask.controller;

import com.example.securitytask.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainSecurityController {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login(final ModelMap model, @RequestParam("error") final Optional<String> error) {
        error.ifPresent(e -> model.addAttribute("error", e));
        return "login";
    }

    @GetMapping("/logoutSuccess")
    public String logout() {
        return "logout";
    }
}
