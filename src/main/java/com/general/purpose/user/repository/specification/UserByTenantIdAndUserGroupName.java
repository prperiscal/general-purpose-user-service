package com.general.purpose.user.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.List;
import java.util.UUID;

import com.general.purpose.user.common.Validate;
import com.general.purpose.user.model.User;
import com.general.purpose.user.model.UserGroup;
import com.general.purpose.user.model.UserGroup_;
import com.general.purpose.user.model.User_;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

/**
 * <p>Specification to search user filtering by email and group name.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class UserByTenantIdAndUserGroupName implements Specification<User> {

    @NonNull
    private final UUID tenantId;

    @NonNull
    private final UUID userGroupId;

    @Nullable
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Validate.notNull(root, "root");
        Validate.notNull(criteriaQuery, "criteriaQuery");
        Validate.notNull(criteriaBuilder, "criteriaBuilder");

        List<Predicate> predicates = Lists.newArrayList();
        predicates.add(criteriaBuilder.equal(root.get(User_.tenantId), tenantId));

        SetJoin<User, UserGroup> userGroups = root.join(User_.userGroups);
        predicates.add(criteriaBuilder.equal(userGroups.get(UserGroup_.id), userGroupId));

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
