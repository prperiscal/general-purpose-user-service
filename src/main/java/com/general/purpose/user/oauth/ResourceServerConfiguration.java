package com.general.purpose.user.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * <p>For restricting an access to the rest endpoints.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @NonNull
    private final TokenStore tokenStore;

    /**
     * <p>>Use this to configure the access rules for secure resources. By default all resources <i>not</i> in "/oauth/**"
     * are protected (but no specific rules about scopes are given, for instance).
     * <p>By default all endpoints under /api/ are protected fot authenticated calls.
     *
     * @param http the current http filter configuration
     *
     * @throws Exception if there is a problem
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/api/**").authenticated().anyRequest().permitAll().and()
            .headers().frameOptions().disable();
    }

    /**
     * <p>Add resource-server specific properties.
     *
     * @param resources configurer for the resource server
     *
     * @throws Exception if there is a problem
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }
}