package com.mytasks.user.facade;

import static com.mytasks.user.TestUtils.getUserGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Optional;
import java.util.stream.Collectors;

import com.mytasks.user.converter.User2UserBase;
import com.mytasks.user.converter.UserGroup2UserGroupBase;
import com.mytasks.user.converter.UserGroup2UserGroupBaseWithUsers;
import com.mytasks.user.facility.ConverterFacility;
import com.mytasks.user.model.User;
import com.mytasks.user.model.UserGroup;
import com.mytasks.user.projection.UserBase;
import com.mytasks.user.projection.UserGroupBase;
import com.mytasks.user.projection.UserGroupBaseWithUsers;
import com.mytasks.user.service.UserGroupService;
import com.prperiscal.spring.resolver.projection.base.Projection;
import com.prperiscal.spring.resolver.projection.base.ProjectionResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupFacadeTest {

    @InjectMocks
    private UserGroupFacade userGroupFacade;

    @Mock
    private UserGroupService userGroupService;

    @Mock
    private ProjectionResolver projectionResolver;

    @Mock
    private ConverterFacility converterFacility;

    @Test
    public void findOneTest() throws Exception {
        UserGroup userGroup = getUserGroup();

        UserGroup2UserGroupBase userGroup2UserGroupBase = new UserGroup2UserGroupBase();

        Mockito.when(projectionResolver.resolve(eq(UserGroup.class), any())).thenReturn(
                Optional.of(UserGroupBase.class));
        Mockito.when(userGroupService.findOne(userGroup.getTenantId(), userGroup.getId())).thenReturn(userGroup);
        Mockito.when(converterFacility.convert(userGroup, UserGroupBase.class)).thenReturn(
                userGroup2UserGroupBase.convert(userGroup));

        Projection userGroupProjection = userGroupFacade.findOne(userGroup.getTenantId(), userGroup.getId(), "null");
        assertThat(userGroupProjection).isInstanceOf(UserGroupBase.class);

        UserGroupBase userGroupBase = (UserGroupBase) userGroupProjection;
        assertThat(userGroupBase).isNotNull();
        assertThat(userGroupBase.getName()).isEqualTo("group1");
    }

    @Test
    public void findOneWithUsersTest() throws Exception {
        UserGroup userGroup = getUserGroup();

        Mockito.when(projectionResolver.resolve(eq(UserGroup.class), any())).thenReturn(
                Optional.of(UserGroupBaseWithUsers.class));
        Mockito.when(userGroupService.findOne(userGroup.getTenantId(), userGroup.getId())).thenReturn(userGroup);

        User2UserBase user2UserBase = new User2UserBase();
        for(User user : userGroup.getUsers()) {
            Mockito.when(converterFacility.convert(user, UserBase.class)).thenReturn(user2UserBase.convert(user));
        }

        UserGroup2UserGroupBaseWithUsers userGroup2UserGroupBaseWithUsers = new UserGroup2UserGroupBaseWithUsers(
                converterFacility);
        UserGroupBaseWithUsers userGroupBaseWithUsersMocked = userGroup2UserGroupBaseWithUsers.convert(userGroup);
        Mockito.when(converterFacility.convert(userGroup, UserGroupBaseWithUsers.class)).thenReturn(
                userGroupBaseWithUsersMocked);

        Projection userGroupProjection = userGroupFacade.findOne(userGroup.getTenantId(), userGroup.getId(),
                                                                 "UserGroupBaseWithUsers");
        assertThat(userGroupProjection).isInstanceOf(UserGroupBaseWithUsers.class);

        UserGroupBaseWithUsers userGroupBaseWithUsers = (UserGroupBaseWithUsers) userGroupProjection;
        assertThat(userGroupBaseWithUsers).isNotNull();
        assertThat(userGroupBaseWithUsers.getName()).isEqualTo("group1");
        assertThat(userGroupBaseWithUsers.getUsers().stream().map(UserBase::getId)).containsExactlyInAnyOrderElementsOf(
                userGroup.getUsers().stream().map(User::getId).collect(Collectors.toSet()));
    }

}