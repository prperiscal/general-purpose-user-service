package com.general.purpose.user.rest;

/**
 * <p>Binding for {@link RoleCtrl}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
interface RoleBinding {

    String ROLES = "roles";

    String BASE_PATH = "/api/" + ROLES;
    String FIND_ALL_PATH = BASE_PATH + "/";

}
