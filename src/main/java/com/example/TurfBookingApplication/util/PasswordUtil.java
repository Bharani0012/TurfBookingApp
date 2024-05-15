package com.example.TurfBookingApplication.util;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordUtil {

    private static final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 4);


    public static String encodePassword(String password) {
        return encoder.encode(password);
    }

    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}

