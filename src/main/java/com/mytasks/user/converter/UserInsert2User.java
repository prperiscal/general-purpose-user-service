package com.mytasks.user.converter;

import com.mytasks.user.model.User;
import com.mytasks.user.rest.input.UserInsert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link UserInsert} to a {@link User}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
public class UserInsert2User implements Converter<UserInsert, User> {

    @Override
    public User convert(@Nullable UserInsert userInsert) {
        if(userInsert == null) {
            return null;
        }

        final User user = new User();
        user.setTenantId(userInsert.getTenantId());
        user.setEmail(userInsert.getEmail());
        user.setName(userInsert.getName());
        user.setPassword(userInsert.getPassword());
        user.setRole(userInsert.getRole());
        return user;
    }
}
