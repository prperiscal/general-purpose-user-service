package com.general.purpose.user.rest;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import com.general.purpose.user.exception.UserGroupNotFoundException;
import com.general.purpose.user.exception.UserNotFoundException;
import com.general.purpose.user.facade.UserGroupFacade;
import com.general.purpose.user.model.User;
import com.general.purpose.user.model.UserGroup;
import com.general.purpose.user.rest.input.IncludeUsers;
import com.general.purpose.user.rest.input.RemoveUsers;
import com.general.purpose.user.rest.input.UserGroupInsert;
import com.general.purpose.user.rest.input.UserInsert;
import com.general.purpose.user.rest.input.UserUpdate;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Rest controller for {@link UserGroup UserGroups}.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@RestController
public class UserGroupCtrl implements UserGroupBinding {

    @NonNull
    private final UserGroupFacade userGroupFacade;

    /**
     * <p>Retrieves {@link UserGroup} with the given userGroupId.
     *
     * @param tenantId       {@link UUID} of tenant
     * @param userGroupId    {@link UUID} of userGroup
     * @param projectionName the name of the projection the {@link UserGroup} shall be converted to
     *
     * @return The requested {@link Projection} for the UserGroup
     * @throws UserNotFoundException    if {@link UserGroup} has not being found
     * @throws DataAccessException      if database access fails
     * @throws IllegalArgumentException if any parameter is invalid, like unmatched uuid format for ids
     * @since 1.0.0
     */
    @RequestMapping(method = GET, path = FIND_ONE_PATH, produces = APPLICATION_JSON_UTF8_VALUE)
    public Projection findOne(@PathVariable UUID tenantId, @PathVariable UUID userGroupId,
                              @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName) {
        return Optional.ofNullable(userGroupFacade.findOne(tenantId, userGroupId, projectionName)).orElseThrow(
                () -> new UserNotFoundException(tenantId.toString(), userGroupId.toString()));
    }

    /**
     * <p>Delete the requested {@link UserGroup}.
     *
     * @param tenantId    {@link UUID} of tenant
     * @param userGroupId {@link UUID} of userGroup
     *
     * @return 204 if deleting was success
     * <p>404 {@link UserGroup} with userId is not exists
     * <p>400 if an exception occurs
     * @throws IllegalStateException    if deletion fails with severe problems
     * @throws IllegalArgumentException if parameters are invalid
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @RequestMapping(method = DELETE, path = DELETE_PATH)
    public ResponseEntity<?> delete(@PathVariable UUID tenantId, @PathVariable UUID userGroupId) {
        boolean deleted = userGroupFacade.delete(tenantId, userGroupId);
        HttpStatus status = deleted ? NO_CONTENT : NOT_FOUND;
        return ResponseEntity.status(status).build();
    }

    /**
     * <p>Inserts a new {@link UserGroup}.
     *
     * @param userGroupInsert {@link UserInsert} userGroup to insert
     * @param projectionName  the name of the projection the {@link UserGroup} shall be converted to
     *
     * @return {@link UserGroup} created with Status 201
     * @throws DataAccessException if database access fails
     * @since 1.0.0
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = POST, path = INSERT_PATH, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Projection insert(@RequestBody @Valid UserGroupInsert userGroupInsert,
                             @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName) {
        return userGroupFacade.insert(userGroupInsert, projectionName);
    }

    /**
     * <p>Includes a new {@link User useres} inside a given group.
     *
     * @param tenantId     {@link UUID} of tenant id
     * @param userGroupId  {@link UUID} of userGroup
     * @param includeUsers {@link IncludeUsers} users info to be included
     *
     * @return 204, "No Content"
     * @throws UserGroupNotFoundException if no userGroup has been found
     * @throws DataAccessException        if database access fails
     * @since 1.0.0
     */
    //Arguably this request can be defined as PUT, coz the user is being updated, but the deletion should also be considered PUT too, and
    //makes sense to distinguish from both of them.
    @RequestMapping(method = POST, path = ADD_USER_PATH, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> addUser(@PathVariable UUID tenantId, @PathVariable UUID userGroupId,
                                          @RequestBody @Valid IncludeUsers includeUsers) {
        userGroupFacade.addUsers(tenantId, userGroupId, includeUsers);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * <p>Removes a {@link User users} from the given group.
     *
     * @param tenantId    {@link UUID} of tenant id
     * @param userGroupId {@link UUID} of userGroup
     * @param removeUsers {@link UserUpdate} users info to be removed
     *
     * @return 204, "No Content"
     * @throws UserGroupNotFoundException if no userGroup has been found
     * @throws DataAccessException        if database access fails
     * @since 1.0.0
     */
    @RequestMapping(method = DELETE, path = REMOVE_USER_PATH, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> removeUser(@PathVariable UUID tenantId, @PathVariable UUID userGroupId,
                                             @RequestBody @Valid RemoveUsers removeUsers) {
        userGroupFacade.removeUsers(tenantId, userGroupId, removeUsers);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
