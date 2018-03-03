package com.general.purpose.user.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Set;

import com.general.purpose.user.facade.RoleFacade;
import com.general.purpose.user.model.Role;
import com.general.purpose.user.projection.RoleBase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Rest controller for {@link Role Roles}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@RestController
public class RoleCtrl implements RoleBinding {

    @NonNull
    private final RoleFacade roleFacade;

    /**
     * Retrieve all {@link Role} entities
     *
     * @return Set of {@link Set< RoleBase > roles}
     * @since 1.0.0
     */
    @RequestMapping(method = GET, path = FIND_ALL_PATH, produces = APPLICATION_JSON_UTF8_VALUE)
    public Set<RoleBase> findAll() {
        return roleFacade.findAll();
    }
}
