package com.example.utils.encoder.fake;

import com.example.utils.encoder.PwEncoder;


public class TestEncoderBean implements PwEncoder {
    @Override
    public String encode(String pw) {
        return null;
    }

    @Override
    public boolean match(String rawPw, String encodedPw) {
        return false;
    }
}
