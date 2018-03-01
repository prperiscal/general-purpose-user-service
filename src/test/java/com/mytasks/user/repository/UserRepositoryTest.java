package com.mytasks.user.repository;

import static com.google.common.collect.Sets.newHashSet;
import static com.mytasks.user.CommonTestVars.PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import com.mytasks.user.ServiceApplication;
import com.mytasks.user.model.Role;
import com.mytasks.user.model.User;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(value = PROFILE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@SpringDataCompose
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DataComposeResource("baseUserUserGroup.json")
    public void findByTenantIdAndId() throws Exception {
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
    public void findByEmail() throws Exception {
        String email = "prperiscal1@gmail.com";

        Page<User> userPage = userRepository.findByEmail(email, Pageable.unpaged());

        List<User> users = userPage.getContent();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.stream().map(User::getName)).containsExactlyInAnyOrderElementsOf(
                newHashSet("Pablo1", "Pablo tenant2"));
    }

    @Test
    public void deleteByTenantIdAndId() throws Exception {
    }

    @Test
    public void findByTenantIdAndIdIsIn() throws Exception {
    }

}
