package com.mytasks.user.repository;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.Set;
import java.util.UUID;

import com.mytasks.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Repository to provide access to {@link User} objects.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Transactional(propagation = MANDATORY, readOnly = true)
    User findByTenantIdAndId(UUID tenantId, UUID id);

    @Transactional(propagation = MANDATORY, readOnly = true)
    Page<User> findByEmail(String email, Pageable pageable);

    @Transactional(propagation = MANDATORY)
    long deleteByTenantIdAndId(UUID tenantId, UUID id);

    @Transactional(propagation = MANDATORY, readOnly = true)
    Set<User> findByTenantIdAndIdIsIn(UUID tenantId, Set<UUID> userIds);

}
