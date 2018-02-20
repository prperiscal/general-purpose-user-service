package com.mytasks.user.rest;

/**
 * <p>Binding constants related to the corresponding rest endpoint.
 *
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
    String INSERT_PATH = BASE_PATH;
    String UPDATE_PATH = BASE_PATH + "/{userId}";
    String DELETE_PATH = BASE_PATH + "/{userId}";
    String FIND_BY_EMAIL_DOMAIN_PATH = FIND_ONE_PATH + "/withChangedEmailDomain";
    String FIND_USER_GROUPS_PATH = BASE_PATH + "/{userId}/userGroups";

}
