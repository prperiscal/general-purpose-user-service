package com.mytasks.user.rest;

/**
 * <p>Binding for {@link UserGroupCtrl}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
interface UserGroupBinding {

    String USER_GROUP = "userGroup";
    String USER_GROUPS = "userGroups";
    String SEARCH = "search";

    String PROJECTION_NAME_PARAM = "projection";

    String BASE_PATH = "api/tenants/{tenantId}/" + USER_GROUPS;
    String FIND_ONE_PATH = BASE_PATH + "/{userGroupId}";
    String INSERT_PATH = "api/" + USER_GROUP;
    String DELETE_PATH = BASE_PATH + "/{userGroupId}";
    String ADD_USER_PATH = BASE_PATH + "/{userGroupId}/userGroups";
    String REMOVE_USER_PATH = BASE_PATH + "/{userGroupId}/userGroups";
}
