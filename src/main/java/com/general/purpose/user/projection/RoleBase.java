package com.general.purpose.user.projection;

import com.general.purpose.user.model.Role;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.Data;

/**
 * <p>A {@link Projection} for an {@link Role} resource containing the base attributes.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
public class RoleBase implements Projection {

    public final static String PROP_NAME = "name";
    public final static String PROP_TYPE = "type";
    public final static String PROP_ACCESS_LEVEL = "accessLevel";

    private String type;
    private String name;
    private int accessLevel;
}
