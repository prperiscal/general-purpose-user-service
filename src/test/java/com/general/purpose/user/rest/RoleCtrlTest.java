package com.general.purpose.user.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.general.purpose.user.CommonTestVars;
import com.general.purpose.user.ServiceApplication;
import com.general.purpose.user.model.Role;
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
@SpringBootTest(classes = ServiceApplication.class)
@WebAppConfiguration
public class RoleCtrlTest {

    @Setter(onMethod = @__({@Autowired}))
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void findAllTest() throws Exception {
        String url = RoleBinding.FIND_ALL_PATH;
        MockHttpServletRequestBuilder builder = get(url);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$", hasSize(Role.values().length)));
    }

}