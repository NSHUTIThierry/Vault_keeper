package com.example.vault_keeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.vault_keeper.model.User;
import com.example.vault_keeper.model.VaultEntry;
import com.example.vault_keeper.repository.VaultRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    private VaultRepository vaultRepo;

    @GetMapping
    public String listVault(@RequestParam(required = false) String q, 
                            HttpSession session, Model model,
                            @CookieValue(value = "lastPlatform", defaultValue = "") String lastPlatform) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login"; // Access Control

        List<VaultEntry> entries;
        if (q != null && !q.isEmpty()) {
            entries = vaultRepo.findByUserAndPlatformNameContainingIgnoreCase(user, q);
        } else {
            entries = vaultRepo.findByUser(user);
        }

        model.addAttribute("entries", entries);
        model.addAttribute("lastPlatformCookie", lastPlatform); // Cookie State
        return "vault";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute VaultEntry entry, HttpSession session, HttpServletResponse response) {
        User user = (User) session.getAttribute("loggedUser");
        
        // FR-2 Validation: Secret must be at least 4 characters
        if (entry.getSecretWord().length() < 4 || entry.getPlatformName().length() < 4) {
            return "redirect:/vault?error=invalidInput"; // Triggers FR-6 400 error logic
        }

        entry.setUser(user);
        vaultRepo.save(entry);

        // State Management: Cookie with 7-day expiry
        Cookie cookie = new Cookie("lastPlatform", entry.getPlatformName());
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return "redirect:/vault";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        // State Management: URL/Path Variable usage
        VaultEntry entry = vaultRepo.findById(id).orElseThrow();
        model.addAttribute("entry", entry);
        return "edit";
    }

    @PostMapping("/update")
    public String updateEntry(@ModelAttribute VaultEntry entry, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        // Hidden field carries the ID
        entry.setUser(user);
        vaultRepo.save(entry);
        return "redirect:/vault";
    }

    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        // FR-5: Delete must be POST
        vaultRepo.deleteById(id);
        return "redirect:/vault";
    }
}