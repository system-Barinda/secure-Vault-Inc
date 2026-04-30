package com.tumba.secure.Vault.Inc.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tumba.secure.Vault.Inc.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}