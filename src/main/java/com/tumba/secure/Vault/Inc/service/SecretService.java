package com.tumba.secure.Vault.Inc.service;

import com.tumba.secure.Vault.Inc.model.Secret;
import com.tumba.secure.Vault.Inc.model.User;
import com.tumba.secure.Vault.Inc.repository.SecretRepository;
import com.tumba.secure.Vault.Inc.security.EncryptionUtil;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecretService {

    private final SecretRepository repo;

    public SecretService(SecretRepository repo) {
        this.repo = repo;
    }

    // ✅ Save secret with validation + encryption
    public void save(Secret secret) {

        if (secret == null || secret.getContent() == null || secret.getContent().length() < 4) {
            throw new IllegalArgumentException("Secret must be at least 4 characters");
        }

        try {
            String encrypted = EncryptionUtil.encrypt(secret.getContent());
            secret.setContent(encrypted);

            repo.save(secret);

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting secret", e);
        }
    }

    // ✅ Get user secrets with safe decryption
    public List<Secret> getUserSecrets(User user) {

        if (user == null) {
            return new ArrayList<>();
        }

        List<Secret> list = repo.findByUser(user);
        List<Secret> result = new ArrayList<>();

        for (Secret s : list) {
            try {
                Secret copy = new Secret();

                // ✅ Now works because setId exists
                copy.setId(s.getId());
                copy.setTitle(s.getTitle());
                copy.setUser(s.getUser());

                if (s.getContent() != null) {
                    String decrypted = EncryptionUtil.decrypt(s.getContent());
                    copy.setContent(decrypted);
                }

                result.add(copy);

            } catch (Exception e) {
                throw new RuntimeException("Error decrypting secret", e);
            }
        }

        return result;
    }

    // ✅ Delete secret
    public void delete(Long id) {
        if (id != null) {
            repo.deleteById(id);
        }
    }
    public Secret findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
}