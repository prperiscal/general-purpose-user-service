package com.mytasks.user.repository;

import static com.mytasks.user.CommonTestVars.PROFILE;

import com.prperiscal.spring.data.compòse.SpringDataCompose;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(value = PROFILE)
@RunWith(SpringRunner.class)
@SpringDataCompose()
public class UserRepositoryTest {

    @Test
    public void findByTenantIdAndId() throws Exception {
        System.out.printf("se");
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