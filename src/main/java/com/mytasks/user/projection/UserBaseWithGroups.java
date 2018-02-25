package com.mytasks.user.projection;

import java.util.Set;

import com.mytasks.user.model.User;
import com.mytasks.user.model.UserGroup;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>A {@link Projection} for an {@link User} resource containing the base attributes and {@link UserGroup groups}.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserBaseWithGroups extends UserBase {

    public final static String PROP_USER_GROUPS = "userGroups";

    private Set<UserGroupBase> userGroups;
}
