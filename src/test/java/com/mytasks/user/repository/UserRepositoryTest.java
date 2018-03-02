package com.mytasks.user.repository;

import com.mytasks.user.ServiceApplication;
import com.mytasks.user.model.Role;
import com.mytasks.user.model.User;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.UUID;

import static com.google.common.collect.Sets.newHashSet;
import static com.mytasks.user.CommonTestVars.PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = PROFILE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@SpringDataCompose
public class UserRepositoryTest {

    @Setter(onMethod = @__({ @Autowired }))
    private UserRepository userRepository;

    @Test
    @DataComposeResource("baseUserUserGroup.json")
    public void findByTenantIdAndId() {
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
    @DataComposeResource("baseUserUserGroup.json")
    public void findByEmail() {
        String email = "prperiscal1@gmail.com";

        Set<User> users = userRepository.findByEmail(email);

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.stream().map(User::getName)).containsExactlyInAnyOrderElementsOf(
                newHashSet("Pablo1", "Pablo tenant2"));
    }

    @Test
    @DataComposeResource("baseUserUserGroup.json")
    public void deleteByTenantIdAndId() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        Long result = userRepository.deleteByTenantIdAndId(tenant, userId);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void findByTenantIdAndIdIsIn() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userId2 = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");


        Set<User> users = userRepository.findByTenantIdAndIdIsIn(tenant, newHashSet(userId, userId1, userId2));
        assertThat(users).isNotEmpty();
    }

}
