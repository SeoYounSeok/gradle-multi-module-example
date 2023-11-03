package com.example.utils.encoder;

public interface PwEncoder {
    
    String encode(String pw);

    boolean match(String rawPw, String encodedPw);
    
}
