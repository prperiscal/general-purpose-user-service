package com.general.purpose.user.facade;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.general.purpose.user.CommonTestVars;
import com.general.purpose.user.ServiceApplication;
import com.general.purpose.user.model.Role;
import com.general.purpose.user.projection.UserBase;
import com.general.purpose.user.projection.UserBaseWithGroups;
import com.general.purpose.user.projection.UserGroupBase;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import com.prperiscal.spring.resolver.projection.base.Projection;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(value = CommonTestVars.PROFILE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@SpringDataCompose
public class UserFacadeTest {

    @Setter(onMethod = @__({@Autowired}))
    private UserFacade userFacade;

    @Test
    @DataComposeResource("User.json")
    public void findOneUserBaseTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

        Projection userBaseProjection = userFacade.findOne(tenant, userId, "UserBase");
        assertThat(userBaseProjection).isInstanceOf(UserBase.class);

        UserBase userBase = (UserBase) userBaseProjection;
        assertThat(userBase.getEmail()).isEqualTo("prperiscal@gmail.com");
        assertThat(userBase.getName()).isEqualTo("Pablo");
        assertThat(userBase.getRole()).isEqualTo(Role.ADMINISTRATOR);
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findOneUserBaseWithGroupsTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID pablo = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userGroup1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userGroup3 = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");

        Projection userBaseProjection = userFacade.findOne(tenant, pablo, "UserBaseWithGroups");
        assertThat(userBaseProjection).isInstanceOf(UserBaseWithGroups.class);

        UserBaseWithGroups userBaseWithGroups = (UserBaseWithGroups) userBaseProjection;
        assertThat(userBaseWithGroups.getUserGroups().stream().map(UserGroupBase::getId)).containsExactlyInAnyOrder(
                userGroup1, userGroup3);
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findGroupMatesTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID pablo = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID juan = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID pedro = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        UUID raquel = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");

        Page<? extends Projection> userPage = userFacade.findGroupMates(tenant, pablo, "UserBase", Pageable.unpaged());
        assertThat(userPage.getTotalElements()).isEqualTo(3);

        List<UserBase> userBases = (List<UserBase>) userPage.getContent();
        assertThat(userBases.stream().map(UserBase::getId)).containsExactlyInAnyOrder(juan, pedro, raquel);
    }

    @Test
    @DataComposeResource("User.json")
    public void findByEmailTest() throws Exception {
        Set<? extends Projection> users = userFacade.findByEmail("prperiscal@gmail.com", "UserBase");

        assertThat(users.size()).isEqualTo(1);

        UserBase user = (UserBase) users.stream().findFirst().get();
        assertThat(user.getEmail()).isEqualTo("prperiscal@gmail.com");
        assertThat(user.getName()).isEqualTo("Pablo");
        assertThat(user.getRole()).isEqualTo(Role.ADMINISTRATOR);
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findByEmailWithGroupsTest() throws Exception {
        String email = "prperiscal@gmail.com";
        UUID userGroup1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userGroup3 = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");

        Set<? extends Projection> users = userFacade.findByEmail(email, "UserBaseWithGroups");

        assertThat(users.size()).isEqualTo(1);

        UserBaseWithGroups userBaseWithGroups = (UserBaseWithGroups) users.stream().findFirst().get();
        assertThat(userBaseWithGroups.getEmail()).isEqualTo(email);
        assertThat(userBaseWithGroups.getName()).isEqualTo("Pablo");
        assertThat(userBaseWithGroups.getRole()).isEqualTo(Role.WORKER);
        assertThat(userBaseWithGroups.getUserGroups().stream().map(UserGroupBase::getId)).containsExactlyInAnyOrder(
                userGroup1, userGroup3);
    }

    @Test
    @DataComposeResource("User.json")
    public void delete() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

        Boolean deleted = userFacade.delete(tenant, userId);
        assertThat(deleted).isTrue();

        deleted = userFacade.delete(tenant, userId);
        assertThat(deleted).isFalse();
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findByUserGroupTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userGroup = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
        UUID pablo = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID pedro = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        UUID raquel = UUID.fromString("550e8400-e29b-41d4-a716-446655440004");

        Page<? extends Projection> userPage = userFacade.findByUserGroup(tenant, userGroup, "SomeWrongProjection",
                                                                         Pageable.unpaged());
        assertThat(userPage.getTotalElements()).isEqualTo(3);

        List<UserBase> users = (List<UserBase>) userPage.getContent();
        assertThat(users.stream().map(UserBase::getId)).containsExactlyInAnyOrder(pablo, pedro, raquel);
    }

}