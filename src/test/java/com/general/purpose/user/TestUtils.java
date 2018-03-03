package com.general.purpose.user;

import java.util.UUID;

import com.general.purpose.user.model.User;
import com.general.purpose.user.model.UserGroup;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    public static UserGroup getUserGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName("group1");
        userGroup.setTenantId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        userGroup.setTid(1L);

        userGroup.setUsers(Sets.newHashSet(createUser("pablo"), createUser("juan"), createUser("raquel")));
        return userGroup;
    }

    public static User createUser(String name) {
        User user = new User();
        user.setName(name);
        user.setEmail(name + "@gmail.com");
        user.setPassword("pass");
        user.setTenantId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        return user;
    }

}
