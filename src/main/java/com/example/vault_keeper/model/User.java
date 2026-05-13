package com.example.vault_keeper.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Logic: RegNo1

    @Column(nullable = false)
    private String password; // Logic: RegNo2 (Hashed)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<VaultEntry> vaultEntries;

    public User() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<VaultEntry> getVaultEntries() { return vaultEntries; }
    public void setVaultEntries(List<VaultEntry> vaultEntries) { this.vaultEntries = vaultEntries; }
}