package com.example.vault_keeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.vault_keeper.model.User;
import com.example.vault_keeper.repository.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, 
                        HttpSession session, Model model) {
        // Logic: RegNo1 and RegNo2 matching
        User user = userRepo.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            // Requirement: Session management
            session.setAttribute("loggedUser", user);
            return "redirect:/vault";
        }
        
        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Requirement: Logout invalidates the session
        session.invalidate();
        return "redirect:/login";
    }
}