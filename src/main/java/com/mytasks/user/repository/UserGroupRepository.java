package com.mytasks.user.repository;

import com.mytasks.user.model.UserGroup;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

/**
 * <p>Repository to provide access to {@link UserGroup} objects.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Repository
public interface UserGroupRepository extends PagingAndSortingRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {

    @Transactional(propagation = MANDATORY, readOnly = true)
    UserGroup findByTenantIdAndId(UUID tenantId, UUID id);

    @Transactional(propagation = MANDATORY)
    long deleteByTenantIdAndId(UUID tenantId, UUID id);
}
