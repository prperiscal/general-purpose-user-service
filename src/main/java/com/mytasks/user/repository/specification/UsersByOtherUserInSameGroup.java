package com.mytasks.user.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.mytasks.user.common.Validate;
import com.mytasks.user.model.User;
import com.mytasks.user.model.UserGroup;
import com.mytasks.user.model.UserGroup_;
import com.mytasks.user.model.User_;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

/**
 * <p>Specification to search users within the same group(s) as the given user.
 * <p>
 * <P>For the given user id, all belongings groups will be fetched.
 * <p>For each of theses groups, all users will be returned.
 * <p>No repeating users and excluding the given user.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class UsersByOtherUserInSameGroup implements Specification<User> {

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

        //@formatter:off
        /*
        select distinct user0_.tid as tid1_0_,
                        user0_.id as id2_0_,
                        user0_.tenant_id as tenant_i3_0_,
                        user0_.email as email4_0_,
                        user0_.name as name5_0_,
                        user0_.password as password6_0_,
                        user0_.role as role7_0_
        from user_service.user user0_ inner join user_service.user_group_users usergroups2_ on
                    user0_.tid=usergroups2_.users_tid inner join user_service.user_group usergroup3_ on
                            usergroups2_.user_groups_tid=usergroup3_.tid
                                cross join user_service.user user1_ inner join user_service.user_group_users usergroups4_ on
                                user1_.tid=usergroups4_.users_tid inner join user_service.user_group usergroup5_
                                on usergroups4_.user_groups_tid=usergroup5_.tid
        where user1_.id=?
                   and (usergroup3_.id in (usergroup5_.id))
                   and user0_.tenant_id=?
                   and user0_.id<>? limit ?
         */
        ////@formatter:on

        List<Predicate> predicates = Lists.newArrayList();

        Root<User> userGiven = criteriaQuery.from(User.class);
        predicates.add(criteriaBuilder.equal(userGiven.get(User_.id), userId));
        SetJoin<User, UserGroup> userGroupsGiven = userGiven.join(User_.userGroups);

        SetJoin<User, UserGroup> userGroups = root.join(User_.userGroups);
        predicates.add(userGroups.get(UserGroup_.id).in(userGroupsGiven.get(UserGroup_.id)));

        predicates.add(criteriaBuilder.equal(root.get(User_.tenantId), tenantId));
        predicates.add(criteriaBuilder.notEqual(root.get(User_.id), userId));

        criteriaQuery.distinct(true);
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
