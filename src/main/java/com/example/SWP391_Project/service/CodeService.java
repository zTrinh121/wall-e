package com.example.SWP391_Project.service;

public interface CodeService {
    String createCode(String key, String value);
    String getCode(String key);
    void removeCode(String key);
}