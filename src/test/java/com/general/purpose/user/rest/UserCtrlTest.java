package com.general.purpose.user.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.general.purpose.user.CommonTestVars;
import com.general.purpose.user.ServiceApplication;
import com.general.purpose.user.projection.UserBase;
import com.general.purpose.user.projection.UserBaseWithGroups;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(value = CommonTestVars.PROFILE)
@RunWith(SpringRunner.class)
@SpringDataCompose
@SpringBootTest(classes = ServiceApplication.class)
@WebAppConfiguration
public class UserCtrlTest {

    private static final String RESULT = "$.";
    private static final String PAGE_CONTENT = "content";

    @Setter(onMethod = @__({@Autowired}))
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DataComposeResource("User.json")
    public void findOneTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

        String url = UserBinding.FIND_ONE_PATH;
        MockHttpServletRequestBuilder builder = get(url, tenant, userId);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isOk());
        result.andExpect(jsonPath(RESULT + UserBase.PROP_ID).value(userId.toString()));
        result.andExpect(jsonPath(RESULT + UserBase.PROP_NAME).value("Pablo"));
        result.andExpect(jsonPath(RESULT + UserBase.PROP_EMAIL).value("prperiscal@gmail.com"));
        result.andExpect(jsonPath(RESULT + UserBase.PROP_TENANT_ID).value(tenant.toString()));
        result.andExpect(jsonPath(RESULT + UserBase.PROP_ROLE).value("ADMINISTRATOR"));
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findOneWithGroupsTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID pablo = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        String url = UserBinding.FIND_ONE_PATH + "?projection=UserBaseWithGroups";

        MockHttpServletRequestBuilder builder = get(url, tenant, pablo);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isOk());
        result.andExpect(jsonPath(RESULT + UserBaseWithGroups.PROP_ID).value(pablo.toString()));
        result.andExpect(jsonPath(RESULT + UserBaseWithGroups.PROP_NAME).value("Pablo"));
        result.andExpect(jsonPath(RESULT + UserBaseWithGroups.PROP_EMAIL).value("prperiscal@gmail.com"));
        result.andExpect(jsonPath(RESULT + UserBaseWithGroups.PROP_TENANT_ID).value(tenant.toString()));
        result.andExpect(jsonPath(RESULT + UserBaseWithGroups.PROP_ROLE).value("WORKER"));
        result.andExpect(jsonPath(RESULT + UserBaseWithGroups.PROP_USER_GROUPS, hasSize(2)));
    }

    @Test
    @DataComposeResource("User.json")
    public void findOneDifferentTenantTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

        String url = UserBinding.FIND_ONE_PATH;
        MockHttpServletRequestBuilder builder = get(url, tenant, userId);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isNotFound());
    }

    @Test
    @DataComposeResource("User.json")
    public void findOneNonExistentTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");

        String url = UserBinding.FIND_ONE_PATH;
        MockHttpServletRequestBuilder builder = get(url, tenant, userId);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isNotFound());
    }

    @Test
    @DataComposeResource("UsersWithGroups.json")
    public void findGroupMatesTest() throws Exception {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID pablo = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        String url = UserBinding.FIND_GROUP_MATES_PATH;
        MockHttpServletRequestBuilder builder = get(url, tenant, pablo);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isOk());
        result.andExpect(jsonPath(RESULT + PAGE_CONTENT, hasSize(3)));
    }

    @Test
    public void findByEmailTest() throws Exception {
        //TODO
    }

    @Test
    public void deleteTest() throws Exception {
        //TODO
    }

    @Test
    public void insertTest() throws Exception {
        //TODO
    }

    @Test
    public void updateTest() throws Exception {
        //TODO
    }

    @Test
    public void findByGroupNameTest() throws Exception {
        //TODO
    }

}