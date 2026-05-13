package com.example.vault_keeper.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vault_keeper.model.User;
import com.example.vault_keeper.model.VaultEntry;

import java.util.List;

@Repository
public interface VaultRepository extends JpaRepository<VaultEntry, Long> {

    // Retrieve all entries belonging to a specific authenticated user
    List<VaultEntry> findByUser(User user);

    /**
     * Requirement FR-3: Search/Filter function
     * Filters entries by platform name (case-insensitive) for a specific user.
     */
    List<VaultEntry> findByUserAndPlatformNameContainingIgnoreCase(User user, String platformName);
}