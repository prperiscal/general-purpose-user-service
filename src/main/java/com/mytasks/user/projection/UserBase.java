package com.mytasks.user.projection;

import java.util.UUID;

import com.mytasks.user.model.User;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.Data;

/**
 * <p>A {@link Projection} for an {@link User} resource containing the base attributes.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
public class UserBase implements Projection, BaseProperties {

    public final static String PROP_EMAIL = "email";
    public final static String PROP_NAME = "name";
    public final static String PROP_ROLE = "role";

    private Long tid;
    private UUID id;
    private UUID tenantId;
    private String email;
    private String name;
    private String role;

}
