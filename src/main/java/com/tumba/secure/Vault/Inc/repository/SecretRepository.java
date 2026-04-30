package com.tumba.secure.Vault.Inc.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tumba.secure.Vault.Inc.model.Secret;
import com.tumba.secure.Vault.Inc.model.User;

import java.util.List;

public interface SecretRepository extends JpaRepository<Secret, Long> {
    List<Secret> findByUser(User user);
    List<Secret> findByTitleContainingAndUser(String title, User user);
}