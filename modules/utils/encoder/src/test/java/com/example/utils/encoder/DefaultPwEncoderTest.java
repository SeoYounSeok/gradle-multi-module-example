package com.example.utils.encoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DefaultPwEncoderTest {

    DefaultPwEncoder pwEncoder = new DefaultPwEncoder();

    @Test
    public void test_encode_match_password() {
        String rawPassword = "myPassword";

        String encodedPassword = pwEncoder.encode(rawPassword);

        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        assertTrue(pwEncoder.match(rawPassword, encodedPassword));
    }

    @Test
    public void test_mismatched_password() {
        String rawPassword = "myPassword";
        String incorrectPassword = "incorrectPassword";

        String encodedPassword = pwEncoder.encode(rawPassword);

        assertFalse(pwEncoder.match(incorrectPassword, encodedPassword));
    }

}
