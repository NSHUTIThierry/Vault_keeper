package com.example.vault_keeper.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vault_keeper.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Used for authentication to find user by username (RegNo1)
    User findByUsername(String username);
}