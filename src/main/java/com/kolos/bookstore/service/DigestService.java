package com.kolos.bookstore.service;

public interface DigestService {

    String hash(String input);

    boolean verify(String input, String hashedPassword);
}
