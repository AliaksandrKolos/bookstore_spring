package com.kolos.bookstore.service.impl;


import com.kolos.bookstore.service.DigestService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestServiceImpl implements DigestService {

    @Override
    public String hash(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytesIn = input.getBytes();
            messageDigest.update(bytesIn);
            byte[] bytesOut = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, bytesOut);
            String hash = bigInt.toString(16).toUpperCase();
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
