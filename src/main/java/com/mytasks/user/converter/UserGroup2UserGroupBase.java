package com.mytasks.user.converter;

import com.mytasks.user.model.UserGroup;
import com.mytasks.user.projection.UserGroupBase;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link UserGroup} to a {@link UserGroupBase} projection.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
public class UserGroup2UserGroupBase implements Converter<UserGroup, UserGroupBase> {

    @Override
    public UserGroupBase convert(@Nullable UserGroup userGroup) {
        if(userGroup == null) {
            return null;
        }

        final UserGroupBase userGroupBase = new UserGroupBase();
        userGroupBase.setTid(userGroup.getTid());
        userGroupBase.setId(userGroup.getId());
        userGroupBase.setTenantId(userGroup.getTenantId());
        userGroupBase.setName(userGroup.getName());
        return userGroupBase;
    }

}
