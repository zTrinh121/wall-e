package com.example;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptHashingExample {
    public static void main(String[] args) {
        String password = "your_plain_text_password";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashedPassword);
    }
}
