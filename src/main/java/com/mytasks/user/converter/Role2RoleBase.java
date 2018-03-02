package com.mytasks.user.converter;

import com.mytasks.user.model.Role;
import com.mytasks.user.projection.RoleBase;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Converter to convert an {@link Role} to a {@link RoleBase} projection.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
public class Role2RoleBase implements Converter<Role, RoleBase> {

    @Override
    public RoleBase convert(@Nullable Role role) {
        if(role == null) {
            return null;
        }

        final RoleBase roleBase = new RoleBase();
        roleBase.setType(role.name());
        roleBase.setName(role.getName());
        roleBase.setAccessLevel(role.getAccessLevel());
        return roleBase;
    }
}
