package com.mytasks.user.service;

import com.mytasks.user.common.Validate;
import com.mytasks.user.exception.UserNotFoundException;
import com.mytasks.user.facility.ConverterFacility;
import com.mytasks.user.model.User;
import com.mytasks.user.repository.UserRepository;
import com.mytasks.user.repository.specification.UserByTenantIdAndUserGroupName;
import com.mytasks.user.repository.specification.UsersByOtherUserInSameGroup;
import com.mytasks.user.rest.input.UserInsert;
import com.mytasks.user.rest.input.UserUpdate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * <p>Service for {@link User}. Communicates with the user repository directly.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class UserService {

    //TODO: create findByTenant

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final ConverterFacility converterFacility;

    /**
     * <p>Retrieves requested {@link User}.
     *
     * @param tenantId {@link UUID} tenant
     * @param userId   {@link UUID} user
     *
     * @return {@link User}
     * @throws UserNotFoundException    if {@link User} not found
     * @throws IllegalArgumentException if argument is {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public User findOne(UUID tenantId, UUID userId) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");

        return userRepository.findByTenantIdAndId(tenantId, userId);
    }

    /**
     * <p>Retrieves {@link User User(s)} belonging to same group or groups as the given user (by user id).
     *
     * @param tenantId {@link UUID} tenant
     * @param userId   {@link UUID} user
     *
     * @return {@link User}
     * @throws UserNotFoundException    if {@link User} not found
     * @throws IllegalArgumentException if argument is {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public Page<User> findGroupMates(UUID tenantId, UUID userId, Pageable pageable) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");

        Specification<User> specification = new UsersByOtherUserInSameGroup(tenantId, userId);
        return userRepository.findAll(specification, pageable);
    }

    /**
     * <p>Retrieves {@link User} with the given email.
     *
     * @param email    user email
     *
     * @return {@link Set <User>} with the users
     * @throws IllegalArgumentException if email or pageable is {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public Set<User> findByEmail(String email) {
        Validate.notNull(email, "email");

        return userRepository.findByEmail(email);
    }

    /**
     * <p>Delete the requested {@link User}.
     *
     * @param tenantId {@link UUID} of tenant
     * @param userId   {@link UUID} of user
     *
     * @return {@code true} if deleting was success or {@code false} if {@link User} with id is not exists
     * @throws IllegalArgumentException if tenantId or userId are {@code null}
     * @throws IllegalStateException    if deletion fails with severe problems
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional
    public boolean delete(UUID tenantId, UUID userId) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");

        long deleted = userRepository.deleteByTenantIdAndId(tenantId, userId);
        if(deleted > 1) {
            throw new IllegalStateException(
                    String.format("STOP IT. %d objects were deleted but only one was supposed!", deleted));
        }
        return deleted == 1;
    }


    /**
     * <p>Inserts a new {@link User}.
     *
     * @param userInsert the input data containing the information about inserting user
     *
     * @return created {@link User} object
     * @throws IllegalArgumentException if argument is {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional
    public User insert(UserInsert userInsert) {
        Validate.notNull(userInsert, "userInsert");

        User user = converterFacility.convert(userInsert, User.class);
        return userRepository.save(user);
    }


    /**
     * <p>Updates a new {@link User}.
     *
     * @param tenantId   {@link UUID} of tenant
     * @param userId     {@link UUID} of user
     * @param userUpdate the input data containing the information about updating user
     *
     * @return updated {@link User} object
     * @throws UserNotFoundException    if {@link User} not found
     * @throws IllegalArgumentException if argument is not {@code null} but blank
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional
    public User update(UUID tenantId, UUID userId, UserUpdate userUpdate) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userId, "userId");
        Validate.notNull(userUpdate, "userUpdate");

        User user = Optional.ofNullable(userRepository.findByTenantIdAndId(tenantId, userId))
                            .orElseThrow(() -> new UserNotFoundException(tenantId.toString(), userId.toString()));

        Optional.ofNullable(userUpdate.getName()).ifPresent(user::setName);
        Optional.ofNullable(userUpdate.getRole()).ifPresent(user::setRole);
        return userRepository.save(user);
    }

    /**
     * @param tenantId      {@link UUID} of tenant
     * @param userGroupId {@link UUID} name of userGroup
     * @param pageable      page parameters
     *
     * @return {@link Page<User>} with the users
     * @throws IllegalArgumentException if argument is not {@code null} but blank
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public Page<User> findByUserGroup(UUID tenantId, UUID userGroupId, Pageable pageable) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");
        Validate.notNull(pageable, "pageable");

        Specification<User> specification = new UserByTenantIdAndUserGroupName(tenantId, userGroupId);

        return userRepository.findAll(specification, pageable);
    }

}
