package com.general.purpose.user.facade;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.general.purpose.user.common.Validate;
import com.general.purpose.user.exception.UserNotFoundException;
import com.general.purpose.user.facility.ConverterFacility;
import com.general.purpose.user.model.User;
import com.general.purpose.user.projection.UserBase;
import com.general.purpose.user.rest.input.UserInsert;
import com.general.purpose.user.rest.input.UserUpdate;
import com.general.purpose.user.service.UserService;
import com.google.common.collect.Sets;
import com.prperiscal.spring.resolver.projection.base.Projection;
import com.prperiscal.spring.resolver.projection.base.ProjectionResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Facade to hide {@link UserService} logic.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class UserFacade {

    private static final String EVENT_SOURCE = "userFacade";

    @NonNull
    private final UserService userService;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final ApplicationEventPublisher applicationEventPublisher;

    @NonNull
    private final ProjectionResolver projectionResolver;

    @NonNull
    private final ConverterFacility converterFacility;

    /**
     * <p>Finds {@link User} by tenant and user id.
     *
     * @param tenantId       tenant id
     * @param userId         user id
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return corresponded users
     * @throws UserNotFoundException    if {@link User} not found
     * @throws IllegalArgumentException if argument tenantId or userId is empty
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public Projection findOne(UUID tenantId, UUID userId, String projectionName) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");

        final Class<? extends Projection> targetType = projectionResolver.resolve(User.class, projectionName).orElse(
                UserBase.class);
        User user = userService.findOne(tenantId, userId);
        return Optional.ofNullable(user).map(u -> converterFacility.convert(u, targetType)).orElse(null);
    }

    /**
     * <p>Retrieves {@link User User(s)} belonging to same group or groups as the given user (by user id).
     *
     * @param tenantId       tenant id
     * @param userId         user id
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return corresponded users
     * @throws UserNotFoundException    if {@link User} not found
     * @throws IllegalArgumentException if argument tenantId or userId is empty
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public Page<? extends Projection> findGroupMates(UUID tenantId, UUID userId, String projectionName,
                                                     Pageable pageable) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");

        final Class<? extends Projection> targetType = projectionResolver.resolve(User.class, projectionName).orElse(
                UserBase.class);
        Page<User> users = userService.findGroupMates(tenantId, userId, pageable);
        return converterFacility.convert(users, pageable, targetType);
    }

    /**
     * <p>Retrieves {@link User} with the given email.
     *
     * @param email          user email
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return {@link Page<Projection>} with the users
     * @throws IllegalArgumentException if email or pageable is {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public Set<? extends Projection> findByEmail(String email, String projectionName) {
        Validate.notNull(email, "email");

        final Class<? extends Projection> targetType = projectionResolver.resolve(User.class, projectionName).orElse(
                UserBase.class);
        Set<User> users = userService.findByEmail(email);
        return Sets.newHashSet(converterFacility.convert(users, targetType));
    }

    /**
     * <p>Delete the requested {@link User}.
     *
     * @param tenantId UUID of tenant userId
     * @param userId   UUID of user userId
     *
     * @return {@code true} if deleting was success or {@code false} if {@link User} with id is not exists
     * @throws IllegalArgumentException if argument is not {@code null} but blank
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public boolean delete(UUID tenantId, UUID userId) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");

        boolean result = userService.delete(tenantId, userId);
        //        applicationEventPublisher.publishEvent(new UserDeletedEvent(EVENT_SOURCE, userId, tenantId));
        return result;
    }

    /**
     * <p>Inserts the given {@link User}.
     *
     * @param userInsert     {@link UserInsert} user to insert
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return created user object
     * @throws IllegalArgumentException if argument tenantId or userInsert are {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public Projection insert(UserInsert userInsert, String projectionName) {
        Validate.notNull(userInsert, "userInsert");

        userInsert.setPassword(passwordEncoder.encode(userInsert.getPassword()));

        final Class<? extends Projection> targetType = projectionResolver.resolve(User.class, projectionName).orElse(
                UserBase.class);
        User user = userService.insert(userInsert);
        //        applicationEventPublisher.publishEvent(new UserCreatedEvent(EVENT_SOURCE, user, password));
        return converterFacility.convert(user, targetType);
    }

    /**
     * <p>Updates the given {@link User}.
     *
     * @param tenantId       {@link UUID} of tenant
     * @param userId         {@link UUID} of user
     * @param userUpdate     {@link UserUpdate} user to update
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return updated user object
     * @throws UserNotFoundException    if {@link User} not found
     * @throws IllegalArgumentException if argument is not {@code null} but blank
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    public Projection update(UUID tenantId, UUID userId, UserUpdate userUpdate, String projectionName) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");
        Validate.notNull(userUpdate, "userUpdate");

        Class<? extends Projection> targetType = projectionResolver.resolve(User.class, projectionName).orElse(
                UserBase.class);
        User user = userService.update(tenantId, userId, userUpdate);
        //        applicationEventPublisher.publishEvent(new UserUpdatedEvent(EVENT_SOURCE, user));
        return converterFacility.convert(user, targetType);
    }

    /**
     * <p>Find {@link User} by tenant id and group id.
     *
     * @param tenantId       {@link UUID} of tenant
     * @param userGroupId    {@link UUID} name of userGroup
     * @param pageable       page parameters
     * @param projectionName the name of the projection the {@link User} shall be converted to
     *
     * @return {@link Page<Projection>} with the users
     * @throws IllegalArgumentException if argument is not {@code null} but blank
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public Page<? extends Projection> findByUserGroup(UUID tenantId, UUID userGroupId, String projectionName,
                                                      Pageable pageable) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");
        Validate.notNull(pageable, "pageable");

        final Class<? extends Projection> targetType = projectionResolver.resolve(User.class, projectionName).orElse(
                UserBase.class);
        Page<User> users = userService.findByUserGroup(tenantId, userGroupId, pageable);
        return converterFacility.convert(users, pageable, targetType);
    }
}
