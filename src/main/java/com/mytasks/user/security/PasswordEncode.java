package com.mytasks.user.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <p>Class to encode passwords.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class PasswordEncode {
    //TODO: remove this class and just inject PasswordEndocer directly

    @NonNull
    private final PasswordEncoder passwordEncoder;

    /**
     * <p>Encodes the password.
     *
     * @param password to be encoded
     *
     * @return encoded password.
     * @since 1.0.0
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
