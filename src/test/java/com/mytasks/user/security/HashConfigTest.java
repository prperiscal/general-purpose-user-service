package com.mytasks.user.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashConfigTest {

    @Test
    public void ensureEncodeDecodeTest() throws Exception {
        HashConfig hashConfig = new HashConfig();
        PasswordEncoder passwordEncoder = hashConfig.passwordEncoder();

        String encodedPassword = passwordEncoder.encode("test");
        assertThat(encodedPassword).isNotEqualTo("test");

        assertThat(passwordEncoder.matches("test", encodedPassword));
    }

}