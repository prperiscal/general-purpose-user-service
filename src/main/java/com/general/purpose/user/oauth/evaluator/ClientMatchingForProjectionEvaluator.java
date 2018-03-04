package com.general.purpose.user.oauth.evaluator;

import java.util.Optional;

import com.general.purpose.user.projection.UserBaseWithGroupsAndPassword;
import com.prperiscal.spring.resolver.projection.base.Projection;
import com.prperiscal.spring.resolver.projection.base.ProjectionResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

/**
 * <p>Evaluates if the request has access to the requested projection.
 *
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ClientMatchingForProjectionEvaluator {

    @NonNull
    private final ProjectionResolver projectionResolver;

    /**
     * <p>Verifies that the request has access for the given projection
     *
     * @param projection     the projection requested
     * @param authentication {@link Authentication}
     *
     * @return request allowed for the
     * @throws IllegalArgumentException if argument authentication is {@code null}
     * @since 1.0.0
     */
    public boolean evaluate(String projection, OAuth2Authentication authentication) {
        Validate.notNull(authentication, "authentication");

        if(StringUtils.isEmpty(projection) || authentication.isClientOnly()) {
            return true;
        }

        Optional<Class<? extends Projection>> optionalClass = projectionResolver.resolve(projection);

        if(!optionalClass.isPresent()) {
            return true;
        } else {
            Class<? extends Projection> projectionResolved = optionalClass.get();
            Boolean isPasswordProjection = projectionResolved.equals(UserBaseWithGroupsAndPassword.class);
            return !isPasswordProjection;
        }
    }
}