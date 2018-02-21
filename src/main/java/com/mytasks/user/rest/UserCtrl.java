package com.mytasks.user.rest;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import com.mytasks.user.exception.UserNotFoundException;
import com.mytasks.user.facade.UserFacade;
import com.mytasks.user.model.User;
import com.mytasks.user.rest.input.UserInsert;
import com.mytasks.user.rest.input.UserUpdate;
import com.prperiscal.resolver.projection.base.Projection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Rest controller to provide {@link User} content.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@RestController
public class UserCtrl implements UserBinding {

    @NonNull
    private final UserFacade userFacade;

    /**
     * <p>Retrieves {@link User} with the given userId.
     *
     * @param tenantId       {@link UUID} of tenant
     * @param userId         {@link UUID} of user
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return The requested {@link Projection} for the user
     * @throws UserNotFoundException    if {@link User} has not being found
     * @throws DataAccessException      if database access fails
     * @throws IllegalArgumentException if any parameter is invalid, like unmatched uuid format for ids
     * @since 1.0.0
     */
    @RequestMapping(method = GET, path = FIND_ONE_PATH, produces = APPLICATION_JSON_UTF8_VALUE)
    public Projection findOne(@PathVariable UUID tenantId, @PathVariable UUID userId, @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName) {
        return Optional.ofNullable(userFacade.findOne(tenantId, userId, projectionName))
                       .orElseThrow(() -> new UserNotFoundException(tenantId.toString(), userId.toString()));
    }

    /**
     * <p>Retrieves {@link User User(s)} belonging to same group or groups as the given user (by user id).
     *
     * @param tenantId       {@link UUID} of tenant
     * @param userId         {@link UUID} of user
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return The requested {@link Projection} for the users
     * @throws UserNotFoundException    if {@link User} has not being found
     * @throws DataAccessException      if database access fails
     * @throws IllegalArgumentException if any parameter is invalid, like unmatched uuid format for ids
     * @since 1.0.0
     */
    @RequestMapping(method = GET, path = FIND_GROUP_MATES_PATH, produces = APPLICATION_JSON_UTF8_VALUE)
    public Page<? extends Projection> findGroupMates(@PathVariable UUID tenantId, @PathVariable UUID userId,
                                                     @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName,
                                                     Pageable pageable) {
        return userFacade.findGroupMates(tenantId, userId, projectionName, pageable);
    }

    /**
     * <p>Retrieves {@link User} with the given email.
     *
     * @param email          user email
     * @param pageable       page parameters
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return {@link Page<Projection>} with the users
     * @throws NullPointerException if input is {@code null}
     * @throws DataAccessException  if database access fails
     * @since 1.0.0
     */
    @RequestMapping(method = GET, path = FIND_BY_EMAIL_PATH, produces = APPLICATION_JSON_UTF8_VALUE)
    public Page<? extends Projection> findByEmail(@RequestParam(required = false, name = "email") String email,
                                                  @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName,
                                                  Pageable pageable) {
        //If this pageable sounds strange to you, take a look at https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#core.web
        return userFacade.findByEmail(email, projectionName, pageable);
    }

    /**
     * <p>Delete the requested {@link User}.
     *
     * @param tenantId {@link UUID} of tenant
     * @param userId   {@link UUID} of user
     *
     * @return 204 if deleting was success
     * <p>404 {@link User} with userId is not exists
     * <p>400 if an exception occurs
     * @throws IllegalStateException    if deletion fails with severe problems
     * @throws IllegalArgumentException if parameters are invalid
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @RequestMapping(method = DELETE, path = DELETE_PATH)
    public ResponseEntity<?> delete(@PathVariable UUID tenantId, @PathVariable UUID userId) {
        boolean deleted = userFacade.delete(tenantId, userId);
        HttpStatus status = deleted ? NO_CONTENT : NOT_FOUND;
        return ResponseEntity.status(status).build();
    }

    /**
     * <p>Inserts a new {@link User}.
     *
     * @param tenantId       {@link UUID} of tenant id
     * @param userInsert     {@link UserInsert} user to insert
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return {@link User} created with Status 201
     * @throws DataAccessException if database access fails
     * @since 1.0.0
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = POST, path = INSERT_PATH, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Projection insert(@PathVariable UUID tenantId, @RequestBody @Valid UserInsert userInsert,
                             @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName) {
        return userFacade.insert(tenantId, userInsert, projectionName);
    }

    /**
     * <p>Updates {@link User}.
     *
     * @param tenantId       {@link UUID} of tenant id
     * @param userId         {@link UUID} of user
     * @param userUpdate     {@link UserUpdate} information to update
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return {@link User} updated with Status 201
     * @throws DataAccessException if database access fails
     * @since 1.0.0
     */
    @RequestMapping(method = PUT, path = UPDATE_PATH, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Projection update(@PathVariable UUID tenantId, @PathVariable UUID userId,
                             @RequestBody @Valid UserUpdate userUpdate,
                             @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName) {
        return userFacade.update(tenantId, userId, userUpdate, projectionName);
    }

    /**
     * <p>Find {@link User} by group name.
     *
     * @param tenantId       {@link UUID} of tenant id
     * @param groupName      {@link UUID} of user
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return {@link User} updated with Status 201
     * @throws IllegalArgumentException if argument is not {@code null} but blank
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @RequestMapping(method = PUT, path = FIND_USER_GROUPS_PATH, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Page<? extends Projection> findByGroupName(@PathVariable UUID tenantId, @PathVariable String groupName,
                                                      @RequestParam(name = PROJECTION_NAME_PARAM, required = false) String projectionName,
                                                      Pageable pageable) {
        //If this pageable sounds strange to you, take a look at https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#core.web
        return userFacade.findByUserGroup(tenantId, groupName, projectionName, pageable);
    }
}
