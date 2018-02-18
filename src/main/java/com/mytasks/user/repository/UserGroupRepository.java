package com.mytasks.user.repository;

import com.mytasks.user.model.UserGroup;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Repository to provide access to {@link UserGroup} objects.</p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Repository
interface UserGroupRepository extends PagingAndSortingRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {

}
