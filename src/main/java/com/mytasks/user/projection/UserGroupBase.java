package com.mytasks.user.projection;

import java.util.UUID;

import com.mytasks.user.model.UserGroup;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.Data;

/**
 * <p>A {@link Projection} for an {@link UserGroup} resource containing the base attributes.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
public class UserGroupBase implements Projection, BaseProperties {

    public final static String PROP_GROUP_NAME = "name";

    private Long tid;
    private UUID id;
    private UUID tenantId;
    private String name;

}
