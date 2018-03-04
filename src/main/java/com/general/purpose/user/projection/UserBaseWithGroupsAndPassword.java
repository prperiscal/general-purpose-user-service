package com.general.purpose.user.projection;

import java.util.Set;

import com.general.purpose.user.model.User;
import com.general.purpose.user.model.UserGroup;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>A {@link Projection} for an {@link User} resource containing the extended attributes and {@link UserGroup groups}.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserBaseWithGroupsAndPassword extends UserBaseWithGroups {

    public final static String PROP_PASSWORD = "password";

    private Set<UserGroupBase> userGroups;
    private String password;
}
