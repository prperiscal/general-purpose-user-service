package com.mytasks.user.converter;

import com.mytasks.user.model.UserGroup;
import com.mytasks.user.rest.input.UserGroupInsert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link UserGroupInsert} to a {@link UserGroup}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
public class UserGroupInsert2UserGroup implements Converter<UserGroupInsert, UserGroup> {

    @Override
    public UserGroup convert(@Nullable UserGroupInsert userGroupInsert) {
        if(userGroupInsert == null) {
            return null;
        }

        final UserGroup userGroup = new UserGroup();
        userGroup.setName(userGroupInsert.getName());
        userGroup.setTenantId(userGroupInsert.getTenantId());
        return userGroup;
    }
}
