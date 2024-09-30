package com.kolos.bookstore.service;

public interface EncryptionService {

    String hash(String input);

    boolean verify(String input, String hashedPassword);
}
