package com.tumba.secure.Vault.Inc.service;


import com.example.secretapp.security.EncryptionUtil;
import com.tumba.secure.Vault.Inc.model.Secret;
import com.tumba.secure.Vault.Inc.model.User;
import com.tumba.secure.Vault.Inc.repository.SecretRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretService {

    private final SecretRepository repo;

    public SecretService(SecretRepository repo) {
        this.repo = repo;
    }

    public void save(Secret secret) throws Exception {
        secret.setContent(EncryptionUtil.encrypt(secret.getContent()));
        repo.save(secret);
    }

    public List<Secret> getUserSecrets(User user) throws Exception {
        List<Secret> list = repo.findByUser(user);
        for (Secret s : list) {
            s.setContent(EncryptionUtil.decrypt(s.getContent()));
        }
        return list;
    }
}
