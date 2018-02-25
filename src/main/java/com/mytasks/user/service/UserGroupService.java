package com.mytasks.user.service;


import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.mytasks.user.common.Validate;
import com.mytasks.user.exception.UserGroupNotFoundException;
import com.mytasks.user.exception.UserNotFoundException;
import com.mytasks.user.facility.ConverterFacility;
import com.mytasks.user.model.User;
import com.mytasks.user.model.UserGroup;
import com.mytasks.user.repository.UserGroupRepository;
import com.mytasks.user.repository.UserRepository;
import com.mytasks.user.rest.input.IncludeUsers;
import com.mytasks.user.rest.input.RemoveUsers;
import com.mytasks.user.rest.input.UserGroupInsert;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Service for {@link UserGroup}. Communicates with the userGroup repository directly.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class UserGroupService {

    @NonNull
    private final UserGroupRepository userGroupRepository;

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final ConverterFacility converterFacility;

    /**
     * <p>Finds {@link UserGroup} by tenant and user id.
     *
     * @param tenantId    {@link UUID} tenant
     * @param userGroupId {@link UUID} userGroup
     *
     * @return {@link UserGroup}
     * @throws UserNotFoundException    if {@link User} not found
     * @throws IllegalArgumentException if argument is {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public UserGroup findOne(UUID tenantId, UUID userGroupId) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");

        return userGroupRepository.findByTenantIdAndId(tenantId, userGroupId);
    }

    /**
     * <p>Delete the requested {@link UserGroup}.
     *
     * @param tenantId    {@link UUID} of tenant
     * @param userGroupId {@link UUID} of userGroup
     *
     * @return {@code true} if deleting was success or {@code false} if {@link User} with id is not exists
     * @throws IllegalArgumentException if tenantId or userId are {@code null}
     * @throws IllegalStateException    if deletion fails with severe problems
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional
    public boolean delete(UUID tenantId, UUID userGroupId) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userGroupId, "userGroupId");

        long deleted = userGroupRepository.deleteByTenantIdAndId(tenantId, userGroupId);
        if(deleted > 1) {
            throw new IllegalStateException(
                    String.format("STOP IT. %d objects were deleted but only one was supposed!", deleted));
        }
        return deleted == 1;
    }


    /**
     * <p>Inserts a new {@link UserGroup}.
     *
     * @param userGroupInsert the input data containing the information about inserting user
     *
     * @return created {@link User} object
     * @throws NullPointerException if argument is {@code null}
     * @throws DataAccessException  if database access fails
     * @since 1.0.0
     */
    @Transactional
    public UserGroup insert(UserGroupInsert userGroupInsert) {
        Validate.notNull(userGroupInsert, "userGroupInsert");

        UserGroup userGroup = converterFacility.convert(userGroupInsert, UserGroup.class);
        return userGroupRepository.save(userGroup);
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

        UserGroup userGroup = Optional.ofNullable(userGroupRepository.findByTenantIdAndId(tenantId, userGroupId))
                                      .orElseThrow(() -> new UserGroupNotFoundException(tenantId.toString(),
                                                                                        userGroupId.toString()));

        Set<User> users = userRepository.findByTenantIdAndIdIsIn(tenantId, includeUsers.getUserIds());
        userGroup.getUsers().addAll(users);
        userGroupRepository.save(userGroup);
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

        UserGroup userGroup = Optional.ofNullable(userGroupRepository.findByTenantIdAndId(tenantId, userGroupId))
                                      .orElseThrow(() -> new UserGroupNotFoundException(tenantId.toString(),
                                                                                        userGroupId.toString()));

        Set<User> users = userRepository.findByTenantIdAndIdIsIn(tenantId, removeUsers.getUserIds());
        userGroup.getUsers().removeAll(users);
        userGroupRepository.save(userGroup);
    }

}
