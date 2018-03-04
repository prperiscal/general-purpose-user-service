package com.general.purpose.user.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashConfigTest {

    @Test
    public void ensureEncodeDecodeTest() throws Exception {
        HashConfig hashConfig = new HashConfig();
        PasswordEncoder passwordEncoder = hashConfig.passwordEncoder();

        String encodedPassword = passwordEncoder.encode("password");
        assertThat(encodedPassword).isNotEqualTo("password");

        assertThat(passwordEncoder.matches("password", encodedPassword));
    }

}