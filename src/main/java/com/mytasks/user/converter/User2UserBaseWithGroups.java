package com.mytasks.user.converter;

import static java.util.stream.Collectors.toSet;

import com.mytasks.user.facility.ConverterFacility;
import com.mytasks.user.model.User;
import com.mytasks.user.projection.UserBaseWithGroups;
import com.mytasks.user.projection.UserGroupBase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link User} to a {@link UserBaseWithGroups} projection.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Lazy}))
public class User2UserBaseWithGroups implements Converter<User, UserBaseWithGroups> {

    @NonNull
    private final ConverterFacility converterFacility;

    @Override
    public UserBaseWithGroups convert(@Nullable User user) {
        if(user == null) {
            return null;
        }

        final UserBaseWithGroups userBaseWithGroups = new UserBaseWithGroups();
        userBaseWithGroups.setTid(user.getTid());
        userBaseWithGroups.setId(user.getId());
        userBaseWithGroups.setTenantId(user.getTenantId());
        userBaseWithGroups.setEmail(user.getEmail());
        userBaseWithGroups.setName(user.getName());
        userBaseWithGroups.setRole(user.getRole());
        userBaseWithGroups.setUserGroups(user.getUserGroups().stream().map(userGroup -> converterFacility
                .convert(userGroup, UserGroupBase.class)).collect(toSet()));
        return userBaseWithGroups;
    }

}
