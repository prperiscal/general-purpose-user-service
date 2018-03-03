package com.general.purpose.user.repository;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;

import com.general.purpose.user.CommonTestVars;
import com.general.purpose.user.ServiceApplication;
import com.general.purpose.user.model.Role;
import com.general.purpose.user.model.User;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(value = CommonTestVars.PROFILE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@SpringDataCompose
public class UserRepositoryTest {

    @Setter(onMethod = @__({@Autowired}))
    private UserRepository userRepository;

    @Test
    @DataComposeResource("BaseUserUserGroup.json")
    public void findByTenantIdAndIdTest() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        User user = userRepository.findByTenantIdAndId(tenant, userId);

        assertThat(user.getEmail()).isEqualTo("prperiscal@gmail.com");
        assertThat(user.getName()).isEqualTo("Pablo");
        assertThat(user.getRole()).isEqualTo(Role.WORKER);
        assertThat(user.getPassword()).isEqualTo("somePass");
        assertThat(user.getUserGroups()).isNotEmpty();
    }

    @Test
    @DataComposeResource("BaseUserUserGroup.json")
    public void findByEmailTest() {
        String email = "prperiscal1@gmail.com";

        Set<User> users = userRepository.findByEmail(email);

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.stream().map(User::getName)).containsExactlyInAnyOrderElementsOf(
                newHashSet("Pablo1", "Pablo tenant2"));
    }

    @Test
    @DataComposeResource("BaseUserUserGroup.json")
    public void deleteByTenantIdAndIdTest() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        Long result = userRepository.deleteByTenantIdAndId(tenant, userId);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DataComposeResource("BaseUserUserGroup.json")
    public void findByTenantIdAndIdIsInTest() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userId2 = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

        Set<User> users = userRepository.findByTenantIdAndIdIsIn(tenant, newHashSet(userId, userId1, userId2));
        assertThat(users).isNotEmpty();
    }

}
