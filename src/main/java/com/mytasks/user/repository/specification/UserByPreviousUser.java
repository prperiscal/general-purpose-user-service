package com.mytasks.user.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.mytasks.user.common.Validate;
import com.mytasks.user.model.User;
import com.mytasks.user.model.User_;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

/**
 * <p>Specification to search user with "previous" email by a given another user id
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class UserByPreviousUser implements Specification<User> {

    @NonNull
    private final UUID tenantId;

    @NonNull
    private final UUID userId;

    @Nullable
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Validate.notNull(root, "root");
        Validate.notNull(criteriaQuery, "criteriaQuery");
        Validate.notNull(criteriaBuilder, "criteriaBuilder");

        List<Predicate> predicates = Lists.newArrayList();

        Root<User> userGiven = criteriaQuery.from(User.class);
        predicates.add(criteriaBuilder.equal(userGiven.get(User_.id), userId));

        predicates.add(criteriaBuilder
                               .equal(criteriaBuilder.concat(userGiven.get(User_.email), "2"), root.get(User_.email)));
        predicates.add(criteriaBuilder.equal(root.get(User_.tenantId), tenantId));

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
