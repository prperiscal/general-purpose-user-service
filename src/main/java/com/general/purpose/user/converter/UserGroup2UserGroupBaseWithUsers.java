package com.general.purpose.user.converter;

import static java.util.stream.Collectors.toSet;

import com.general.purpose.user.facility.ConverterFacility;
import com.general.purpose.user.model.UserGroup;
import com.general.purpose.user.projection.UserBase;
import com.general.purpose.user.projection.UserGroupBaseWithUsers;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link UserGroup} to a {@link UserGroupBaseWithUsers} projection.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Lazy}))
public class UserGroup2UserGroupBaseWithUsers implements Converter<UserGroup, UserGroupBaseWithUsers> {

    @NonNull
    private final ConverterFacility converterFacility;

    @Override
    public UserGroupBaseWithUsers convert(@Nullable UserGroup userGroup) {
        if(userGroup == null) {
            return null;
        }

        final UserGroupBaseWithUsers userGroupBaseWithUsers = new UserGroupBaseWithUsers();
        userGroupBaseWithUsers.setTid(userGroup.getTid());
        userGroupBaseWithUsers.setId(userGroup.getId());
        userGroupBaseWithUsers.setTenantId(userGroup.getTenantId());
        userGroupBaseWithUsers.setName(userGroup.getName());
        userGroupBaseWithUsers.setUsers(
                userGroup.getUsers().stream().map(user -> converterFacility.convert(user, UserBase.class))
                         .collect(toSet()));
        return userGroupBaseWithUsers;
    }

}
