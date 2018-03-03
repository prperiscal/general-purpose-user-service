package com.general.purpose.user.projection;

import java.util.Set;

import com.general.purpose.user.model.User;
import com.general.purpose.user.model.UserGroup;
import com.google.common.collect.Sets;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>A {@link Projection} for an {@link UserGroup} resource containing the base attributes and {@link User users}.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupBaseWithUsers extends UserGroupBase {

    public final static String PROP_USERS = "users";

    private Set<UserBase> users = Sets.newHashSet();
}
