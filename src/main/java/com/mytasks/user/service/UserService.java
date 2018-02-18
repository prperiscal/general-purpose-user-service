package com.mytasks.user.service;

import java.util.Optional;
import java.util.UUID;

import com.mytasks.user.common.Validate;
import com.mytasks.user.exception.UserNotFoundException;
import com.mytasks.user.facility.ConverterFacility;
import com.mytasks.user.model.User;
import com.mytasks.user.repository.UserRepository;
import com.mytasks.user.rest.input.UserInsert;
import com.mytasks.user.rest.input.UserUpdate;
import com.prperiscal.resolver.projection.base.Projection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Service for {@link User}. Communicates with the user repository directly.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class UserService {

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
     * @throws NullPointerException     if argument is {@code null}
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
     * <p>Retrieves {@link User} with the given email.
     *
     * @param email    user email
     * @param pageable page parameters
     *
     * @return {@link Page<Projection>} with the users
     * @throws IllegalArgumentException if email or pageable is {@code null}
     * @throws DataAccessException      if database access fails
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public Page<User> findByEmail(String email, Pageable pageable) {
        Validate.notNull(email, "email");
        Validate.notNull(pageable, "pageable");

        return userRepository.findByEmail(email, pageable);
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
     * @param tenantId   {@link UUID} of tenant
     * @param userInsert the input data containing the information about inserting user
     *
     * @return created {@link User} object
     * @throws NullPointerException if argument is {@code null}
     * @throws DataAccessException  if database access fails
     * @since 1.0.0
     */
    @Transactional
    public User insert(UUID tenantId, UserInsert userInsert) {
        Validate.notNull(tenantId, "tenantId");
        Validate.notNull(userInsert, "userInsert");

        userInsert.setTenantId(tenantId);

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
        Optional.ofNullable(userUpdate.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userUpdate.getRole()).ifPresent(user::setRole);
        return userRepository.save(user);
    }

}
