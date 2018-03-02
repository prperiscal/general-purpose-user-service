package com.mytasks.user.facade;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

import com.mytasks.user.model.Role;
import com.mytasks.user.projection.RoleBase;
import com.mytasks.user.service.RoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

/**
 * <p>Facade to hide {@link RoleService} logic.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class RoleFacade {

    @NonNull
    private final RoleService roleService;

    @NonNull
    private final ConversionService mvcConversionService;

    /**
     * <p>Retrieve all {@link Role} entities converted to {@link RoleBase}.
     *
     * @return Set of {@link Set<RoleBase> roles}
     * @since 1.0.0
     */
    public Set<RoleBase> findAll() {
        return roleService.findAll().stream().map(role -> mvcConversionService.convert(role, RoleBase.class))
                          .collect(toSet());
    }

}
