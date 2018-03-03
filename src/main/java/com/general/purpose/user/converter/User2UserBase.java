package com.general.purpose.user.converter;

import com.general.purpose.user.model.User;
import com.general.purpose.user.projection.UserBase;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link User} to a {@link UserBase} projection.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
public class User2UserBase implements Converter<User, UserBase> {

    @Override
    public UserBase convert(@Nullable User user) {
        if(user == null) {
            return null;
        }

        final UserBase userBase = new UserBase();
        userBase.setTid(user.getTid());
        userBase.setId(user.getId());
        userBase.setTenantId(user.getTenantId());
        userBase.setEmail(user.getEmail());
        userBase.setName(user.getName());
        userBase.setRole(user.getRole());
        return userBase;
    }

}
