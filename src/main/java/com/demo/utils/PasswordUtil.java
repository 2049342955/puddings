package com.demo.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class PasswordUtil {
    private static Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    public PasswordUtil() {
    }

    public static String encodePassword(String password, String salt) {
        return passwordEncoder.encodePassword(password, salt);
    }
}