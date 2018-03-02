package com.mytasks.user.service;

import com.mytasks.user.ServiceApplication;
import com.mytasks.user.model.Role;
import com.mytasks.user.model.User;
import com.mytasks.user.rest.input.UserInsert;
import com.mytasks.user.rest.input.UserUpdate;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.mytasks.user.CommonTestVars.PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = PROFILE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@SpringDataCompose
public class UserServiceTest {

    @Setter(onMethod = @__({ @Autowired}))
    private UserService userService;

    @Test
    @DataComposeResource("Users.json")
    public void findOne() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        User user = userService.findOne(tenant, userId);
        assertThat(user.getEmail()).isEqualTo("prperiscal@gmail.com");
        assertThat(user.getName()).isEqualTo("Pablo");
        assertThat(user.getRole()).isEqualTo(Role.ADMINISTRATOR);
        assertThat(user.getPassword()).isEqualTo("somePass");
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findGroupMates() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID pablo = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID juan = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID pedro = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        UUID raquel = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");


        Page<User> userPage = userService.findGroupMates(tenant,pablo,Pageable.unpaged());
        assertThat(userPage.getTotalElements()).isEqualTo(3);

        List<User> users = userPage.getContent();
        assertThat(users.stream().map(User::getId)).containsExactlyInAnyOrder(juan, pedro, raquel);

    }

    @Test
    @DataComposeResource("Users.json")
    public void findByEmail() {
        Set<User> users = userService.findByEmail("prperiscal@gmail.com");

        assertThat(users.size()).isEqualTo(1);

        User user = users.stream().findFirst().get();
        assertThat(user.getEmail()).isEqualTo("prperiscal@gmail.com");
        assertThat(user.getName()).isEqualTo("Pablo");
        assertThat(user.getRole()).isEqualTo(Role.ADMINISTRATOR);
        assertThat(user.getPassword()).isEqualTo("somePass");
    }

    @Test
    @DataComposeResource("Users.json")
    public void delete() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        Boolean deleted = userService.delete(tenant, userId);
        assertThat(deleted).isTrue();

        User user = userService.findOne(tenant, userId);
        assertThat(user).isEqualTo(null);
    }

    @Test
    public void insert() {
        String email = "insertedUser@gmail.com";

        UserInsert userInsert = new UserInsert();
        userInsert.setPassword("pass");
        userInsert.setEmail(email);
        userInsert.setName("Inserted User");
        userInsert.setRole(Role.DIRECTOR);
        userInsert.setTenantId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

        User userInserted = userService.insert(userInsert);
        assertThat(userInserted).isNotNull();

        Set<User> users = userService.findByEmail(email);
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    @DataComposeResource("User.json")
    public void update() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        String email = "prperiscal1@gmail.com";
        String name = "new Name";

        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setName(name);
        userUpdate.setRole(Role.DIRECTOR);

        User userUpdated = userService.update(tenant, userId, userUpdate);
        assertThat(userUpdated).isNotNull();

        Set<User> users = userService.findByEmail(email);

        assertThat(users.size()).isEqualTo(1);

        User user = users.stream().findFirst().get();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getRole()).isEqualTo(Role.DIRECTOR);
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findByUserGroup() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userGroup = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
        UUID pablo = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID pedro = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        UUID raquel = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");

        Page<User> userPage = userService.findByUserGroup(tenant,userGroup,Pageable.unpaged());
        assertThat(userPage.getTotalElements()).isEqualTo(3);

        List<User> users = userPage.getContent();
        assertThat(users.stream().map(User::getId)).containsExactlyInAnyOrder(pablo, pedro, raquel);
    }
}
