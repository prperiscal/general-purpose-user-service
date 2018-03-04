package com.general.purpose.user.oauth;

import java.io.IOException;

import com.general.purpose.user.oauth.converter.UserAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

/**
 * <p>Basic class for JWT configuration.</p>
 */
@Configuration
public class JwtConfiguration {

    @Bean
    protected JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(accessTokenConverter());
        final Resource resource = new ClassPathResource("public.key");
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (final IOException e) {
            throw new RuntimeException("Error during reading public certificate file", e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * <p>Default implementation of {@link AccessTokenConverter}.
     * <p>Included with custom converter
     */
    public AccessTokenConverter accessTokenConverter() {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userTokenConverter());
        return accessTokenConverter;
    }

    /**
     * <p>Custom bean for converting a user authentication to and from a Map.
     */
    public UserAuthenticationConverter userTokenConverter() {
        return new UserAuthenticationConverter();
    }
}