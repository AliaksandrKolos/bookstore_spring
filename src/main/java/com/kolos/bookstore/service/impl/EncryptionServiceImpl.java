package com.kolos.bookstore.service.impl;


import com.kolos.bookstore.service.EncryptionService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String hash(String input) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(input, salt);
    }

    public boolean verify(String input, String hashedPassword) {
        return BCrypt.checkpw(input, hashedPassword);
    }
}
