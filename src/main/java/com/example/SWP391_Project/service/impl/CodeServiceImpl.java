package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.service.CodeService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CodeServiceImpl implements CodeService {

    private final ConcurrentHashMap<String, String> codeStore = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public String createCode(String key, String value) {
        codeStore.put(key, value);

        // Schedule the removal of the code after 60 seconds
        scheduler.schedule(new CodeRemover(key), 60, TimeUnit.SECONDS);

        return value;
    }

    @Override
    public String getCode(String key) {
        return codeStore.get(key);
    }

    @Override
    public void removeCode(String key) {
        codeStore.remove(key);
    }

    private class CodeRemover implements Runnable {
        private final String key;

        public CodeRemover(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            codeStore.remove(key);
        }
    }
}
