package com.example.utils.encoder;

import org.mindrot.jbcrypt.BCrypt;

public class DefaultPwEncoder implements PwEncoder {

    @Override
    public String encode(String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    @Override
    public boolean match(String rawPw, String encodedPw) {
        return BCrypt.checkpw(rawPw, encodedPw);
    }
    
}
