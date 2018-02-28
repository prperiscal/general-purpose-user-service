package com.mytasks.user.repository;

import static com.mytasks.user.CommonTestVars.PROFILE;

import com.mytasks.user.ServiceApplication;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @DataComposeResource("testJson.json")
    public void findByTenantIdAndId() throws Exception {
        Assertions.assertThat(userRepository.findAll()).isNotEmpty();
    }

    @Test
    public void findByEmail() throws Exception {
    }

    @Test
    public void deleteByTenantIdAndId() throws Exception {
    }

    @Test
    public void findByTenantIdAndIdIsIn() throws Exception {
    }

}
