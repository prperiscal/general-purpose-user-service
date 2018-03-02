package com.mytasks.user.rest;

/**
 * <p>Binding for {@link UserCtrl}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
interface UserBinding {

    String USER = "user";
    String USERS = "users";
    String SEARCH = "search";

    String PROJECTION_NAME_PARAM = "projection";

    String BASE_PATH = "api/tenants/{tenantId}/" + USERS;
    String FIND_ONE_PATH = BASE_PATH + "/{userId}";
    String FIND_BY_EMAIL_PATH = "api/" + USERS + "/" + SEARCH;
    String INSERT_PATH = "api/" + USER;
    String UPDATE_PATH = BASE_PATH + "/{userId}";
    String DELETE_PATH = BASE_PATH + "/{userId}";
    String FIND_GROUP_MATES_PATH = FIND_ONE_PATH + "/groupMates";
    String FIND_USER_GROUPS_PATH = BASE_PATH + "/{userId}/userGroups";

}
