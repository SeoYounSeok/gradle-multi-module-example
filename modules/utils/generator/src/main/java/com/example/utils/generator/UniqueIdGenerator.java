package com.example.utils.generator;

import java.util.UUID;

public class UniqueIdGenerator {
    
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
    
}
