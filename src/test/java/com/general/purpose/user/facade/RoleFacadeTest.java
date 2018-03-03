package com.general.purpose.user.facade;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import com.general.purpose.user.converter.Role2RoleBase;
import com.general.purpose.user.model.Role;
import com.general.purpose.user.projection.RoleBase;
import com.general.purpose.user.service.RoleService;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

@RunWith(MockitoJUnitRunner.class)
public class RoleFacadeTest {

    @InjectMocks
    private RoleFacade roleFacade;

    @Mock
    private RoleService roleService;

    @Mock
    private ConversionService mvcConversionService;

    @Test
    public void findAllTest() throws Exception {
        Role2RoleBase role2RoleBase = new Role2RoleBase();
        Mockito.when(roleService.findAll()).thenReturn(
                Sets.newHashSet(Role.ADMINISTRATOR, Role.DIRECTOR, Role.LEADER, Role.WORKER));
        Mockito.when(mvcConversionService.convert(Role.ADMINISTRATOR, RoleBase.class)).thenReturn(
                role2RoleBase.convert(Role.ADMINISTRATOR));
        Mockito.when(mvcConversionService.convert(Role.DIRECTOR, RoleBase.class)).thenReturn(
                role2RoleBase.convert(Role.DIRECTOR));
        Mockito.when(mvcConversionService.convert(Role.WORKER, RoleBase.class)).thenReturn(
                role2RoleBase.convert(Role.WORKER));
        Mockito.when(mvcConversionService.convert(Role.LEADER, RoleBase.class)).thenReturn(
                role2RoleBase.convert(Role.LEADER));

        Set<RoleBase> roles = roleFacade.findAll();
        assertThat(roles.stream().map(RoleBase::getType)).containsExactlyInAnyOrder(Role.WORKER.toString(),
                                                                                    Role.DIRECTOR.toString(),
                                                                                    Role.ADMINISTRATOR.toString(),
                                                                                    Role.LEADER.toString());
    }

}