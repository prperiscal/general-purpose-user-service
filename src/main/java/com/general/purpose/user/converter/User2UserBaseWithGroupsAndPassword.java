package com.general.purpose.user.converter;

import static java.util.stream.Collectors.toSet;

import com.general.purpose.user.facility.ConverterFacility;
import com.general.purpose.user.model.User;
import com.general.purpose.user.projection.UserBaseWithGroupsAndPassword;
import com.general.purpose.user.projection.UserGroupBase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link User} to a {@link UserBaseWithGroupsAndPassword} projection.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Lazy}))
public class User2UserBaseWithGroupsAndPassword implements Converter<User, UserBaseWithGroupsAndPassword> {

    @NonNull
    private final ConverterFacility converterFacility;

    @Override
    public UserBaseWithGroupsAndPassword convert(@Nullable User user) {
        if(user == null) {
            return null;
        }

        final UserBaseWithGroupsAndPassword userBaseWithGroupsAndPassword = new UserBaseWithGroupsAndPassword();
        userBaseWithGroupsAndPassword.setTid(user.getTid());
        userBaseWithGroupsAndPassword.setId(user.getId());
        userBaseWithGroupsAndPassword.setTenantId(user.getTenantId());
        userBaseWithGroupsAndPassword.setEmail(user.getEmail());
        userBaseWithGroupsAndPassword.setName(user.getName());
        userBaseWithGroupsAndPassword.setRole(user.getRole());
        userBaseWithGroupsAndPassword.setPassword(user.getPassword());
        userBaseWithGroupsAndPassword.setUserGroups(user.getUserGroups().stream().map(userGroup -> converterFacility
                .convert(userGroup, UserGroupBase.class)).collect(toSet()));
        return userBaseWithGroupsAndPassword;
    }

}
