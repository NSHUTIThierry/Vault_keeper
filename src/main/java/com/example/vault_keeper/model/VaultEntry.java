package com.example.vault_keeper.model;

import javax.persistence.*;

@Entity
@Table(name = "vault_entries")
public class VaultEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String platformName;

    @Column(nullable = false)
    private String secretWord;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public VaultEntry() {}

    /**
     * Requirement FR-3: Secret Masking
     * Masking characters except for the last two.
     */
    public String getMaskedSecret() {
        if (secretWord == null || secretWord.length() <= 2) {
            return secretWord;
        }
        String visiblePart = secretWord.substring(secretWord.length() - 2);
        String maskedPart = "*".repeat(secretWord.length() - 2);
        return maskedPart + visiblePart;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlatformName() { return platformName; }
    public void setPlatformName(String platformName) { this.platformName = platformName; }

    public String getSecretWord() { return secretWord; }
    public void setSecretWord(String secretWord) { this.secretWord = secretWord; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}