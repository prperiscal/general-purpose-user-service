package com.general.purpose.user.repository;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.Set;
import java.util.UUID;

import com.general.purpose.user.model.User;
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
 * "DELETE FROM {h-schema}node_attribute_values nav WHERE nav.node_tid IN ( " + NODE_TIDS_BY_STAGE_TIDS_QUERY + " )";
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Transactional(propagation = MANDATORY, readOnly = true)
    User findByTenantIdAndId(UUID tenantId, UUID id);

    @Transactional(propagation = MANDATORY, readOnly = true)
    Page<User> findByTenantId(UUID tenantId, Pageable pageable);

    @Transactional(propagation = MANDATORY, readOnly = true)
        //    @Query(value = "SELECT u from {h-schema}user u WHERE u.email = :email", nativeQuery = true)
    Set<User> findByEmail(/*@Param("email")*/ String email);

    @Transactional(propagation = MANDATORY)
    long deleteByTenantIdAndId(UUID tenantId, UUID id);

    @Transactional(propagation = MANDATORY, readOnly = true)
    Set<User> findByTenantIdAndIdIsIn(UUID tenantId, Set<UUID> userIds);

}
