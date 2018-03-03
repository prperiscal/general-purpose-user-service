package com.general.purpose.user.service;

import java.util.Set;

import com.general.purpose.user.model.Role;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

/**
 * <p>Service for {@link Role Roles}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Service
public class RoleService {

    /**
     * <p>Retrieve all {@link Role} entities.
     *
     * @return Set of {@link Set<Role> roles}
     * @since 1.0.0
     */
    public Set<Role> findAll() {
        return Sets.newHashSet(Role.values());
    }
}
