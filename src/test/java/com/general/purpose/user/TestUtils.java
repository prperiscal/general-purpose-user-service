package com.general.purpose.user;

import java.util.UUID;

import com.general.purpose.user.model.User;
import com.general.purpose.user.model.UserGroup;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.WebApplicationContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    public static UserGroup getUserGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName("group1");
        userGroup.setTenantId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        userGroup.setTid(1L);

        userGroup.setUsers(Sets.newHashSet(createUser("pablo"), createUser("juan"), createUser("raquel")));
        return userGroup;
    }

    public static User createUser(String name) {
        User user = new User();
        user.setName(name);
        user.setEmail(name + "@gmail.com");
        user.setPassword("pass");
        user.setTenantId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        return user;
    }

    /**
     * <p>Use carefully and only if no other option is possible. Probably you want to use this when dealing with mocking
     * sub-levels BUT consider first if it is possible and desirable to avoid mocking sub-levels and replace it with own tests
     * on these levels.
     * <p>This method gets the bean to be inject with the mock from the application context, unwraps the proxy and insert the
     * mock by force (using reflection).
     *
     * @param classToInjectBean     Class from application contect to be injected with mock.
     * @param injectBean            Bean to inject
     * @param webApplicationContext {@link org.springframework.context.ApplicationContext}
     *
     * @throws Exception if something went wrong
     */
    public static void mockExistingBean(Class<?> classToInjectBean, Object injectBean,
                                        WebApplicationContext webApplicationContext) throws Exception {
        String injectionProperty = StringUtils.uncapitalize(injectBean.getClass().getSimpleName());
        injectionProperty = StringUtils.substringBefore(injectionProperty, "$");
        StaticApplicationContext context = new StaticApplicationContext(webApplicationContext);
        Object beanToBeInjected = context.getBean(classToInjectBean);
        ReflectionTestUtils.setField(unwrapProxy(beanToBeInjected), injectionProperty, injectBean);
        context.refresh();
    }

    private static Object unwrapProxy(Object bean) throws Exception {
        if(AopUtils.isAopProxy(bean) && bean instanceof Advised) {
            Advised advised = (Advised) bean;
            bean = advised.getTargetSource().getTarget();
        }
        return bean;
    }

}
