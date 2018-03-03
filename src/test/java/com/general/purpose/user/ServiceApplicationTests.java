package com.general.purpose.user;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(value = CommonTestVars.PROFILE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class ServiceApplicationTests {

    @Setter(onMethod = @__({@Autowired}))
    private ApplicationContext applicationContext;

    @Test
    public void contextLoadsTest() {
        assertThat(applicationContext).isNotNull();
    }

}
