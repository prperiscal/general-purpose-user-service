package com.mytasks.user.facade;

import java.util.Optional;
import java.util.UUID;

import com.mytasks.user.common.Validate;
import com.mytasks.user.exception.UserGroupNotFoundException;
import com.mytasks.user.exception.UserNotFoundException;
import com.mytasks.user.facility.ConverterFacility;
import com.mytasks.user.model.User;
import com.mytasks.user.model.UserGroup;
import com.mytasks.user.projection.UserGroupBase;
import com.mytasks.user.rest.input.IncludeUsers;
import com.mytasks.user.rest.input.RemoveUsers;
import com.mytasks.user.rest.input.UserGroupInsert;
import com.mytasks.user.service.UserGroupService;
import com.prperiscal.spring.resolver.projection.base.Projection;
import com.prperiscal.spring.resolver.projection.base.ProjectionResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * <p>Facade to hide {@link UserGroupService} logic.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Component
public class UserGroupFacade {

    @NonNull
    private final UserGroupService userGroupService;

    @NonNull
    private final ProjectionResolver projectionResolver;

    @NonNull
    private final ConverterFacility converterFacility;

    /**
     * <p>Finds {@link UserGroup} by tenant and user id.
     *
     * @param tenantId       {@link UUID}of tenant
     * @param userGroupId    {@link UUID} of userGroup
     * @param projectionName the name of the projection the {@link UserGroup} shall be converted to
     *
     * @return corresponded users
     * @throws UserNotFoundException    if {@link UserGroup} not found
     * @throws IllegalArgumentException if argument tenantId or userId is empty
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public Projection findOne(UUID tenantId, UUID userGroupId, String projectionName) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");

        final Class<? extends Projection> targetType = projectionResolver.resolve(UserGroup.class, projectionName)
                                                                         .orElse(UserGroupBase.class);
        UserGroup userGroup = userGroupService.findOne(tenantId, userGroupId);
        return Optional.ofNullable(userGroup).map(u -> converterFacility.convert(u, targetType)).orElse(null);
    }

    /**
     * <p>Delete the requested {@link UserGroup}.
     *
     * @param tenantId    {@link UUID}of tenant
     * @param userGroupId {@link UUID} of userGroup
     *
     * @return {@code true} if deleting was success or {@code false} if {@link User} with id is not exists
     * @throws IllegalArgumentException if argument is not {@code null} but blank
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public boolean delete(UUID tenantId, UUID userGroupId) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");

        boolean result = userGroupService.delete(tenantId, userGroupId);
        //        applicationEventPublisher.publishEvent(new UserDeletedEvent(EVENT_SOURCE, userId, tenantId));
        return result;
    }

    /**
     * <p>Inserts the given {@link UserGroup}.
     *
     * @param userGroupInsert {@link UserGroupInsert} user to insert
     * @param projectionName  the name of the projection the {@link UserGroup} shall be converted to
     *
     * @return created user object
     * @throws IllegalArgumentException if argument tenantId or userInsert are {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public Projection insert(UserGroupInsert userGroupInsert, String projectionName) {
        Validate.notNull(userGroupInsert, "userGroupInsert");

        final Class<? extends Projection> targetType = projectionResolver.resolve(UserGroup.class, projectionName)
                                                                         .orElse(UserGroupBase.class);
        UserGroup userGroup = userGroupService.insert(userGroupInsert);
        //        applicationEventPublisher.publishEvent(new UserCreatedEvent(EVENT_SOURCE, user, password));
        return converterFacility.convert(userGroup, targetType);
    }

    /**
     * <p>Includes {@link User users} into the given {@link UserGroup}.
     *
     * @param tenantId     {@link UUID} of tenant
     * @param userGroupId  {@link UUID} of userGroup
     * @param includeUsers {@link IncludeUsers} containing the users's information to be included
     *
     * @throws IllegalArgumentException   if argument is not {@code null} but blank
     * @throws UserGroupNotFoundException if no userGroup has been found
     * @throws DataAccessException        if database access fails
     * @since 1.0.0
     */
    public void addUsers(UUID tenantId, UUID userGroupId, IncludeUsers includeUsers) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");
        Validate.notNull(includeUsers, "includeUsers");

        userGroupService.addUsers(tenantId, userGroupId, includeUsers);
    }

    /**
     * <p>Removes {@link User} from user group.</p>
     *
     * @param tenantId    {@link UUID} of tenant
     * @param userGroupId {@link UUID} of userGroup
     * @param removeUsers {@link RemoveUsers} containing the users's information to be included
     *
     * @throws IllegalArgumentException   if argument is not {@code null} but blank
     * @throws UserGroupNotFoundException if no userGroup has been found
     * @throws DataAccessException        if database access fails
     * @since 1.0.0
     */
    public void removeUsers(UUID tenantId, UUID userGroupId, RemoveUsers removeUsers) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");
        Validate.notNull(removeUsers, "removeUsers");

        userGroupService.removeUsers(tenantId, userGroupId, removeUsers);
    }

}
