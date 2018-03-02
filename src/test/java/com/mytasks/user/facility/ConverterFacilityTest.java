package com.mytasks.user.facility;

import static com.mytasks.user.TestUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mytasks.user.converter.User2UserBase;
import com.mytasks.user.model.User;
import com.mytasks.user.projection.UserBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class ConverterFacilityTest {

    @InjectMocks
    private ConverterFacility converterFacility;

    @Mock
    private ConversionService conversionService;

    @Test
    public void convertTest() throws Exception {
        User user = createUser("Pablo");
        User2UserBase user2UserBase = new User2UserBase();

        Mockito.when(conversionService.convert(user, UserBase.class)).thenReturn(user2UserBase.convert(user));

        Object converted = converterFacility.convert(user, UserBase.class);
        assertThat(converted).isInstanceOf(UserBase.class);
    }

    @Test
    public void convert1Test() throws Exception {
        Set<User> users = Sets.newHashSet(createUser("Pablo"), createUser("Ana"));
        User2UserBase user2UserBase = new User2UserBase();
        for(User user : users) {
            Mockito.when(conversionService.convert(user, UserBase.class)).thenReturn(user2UserBase.convert(user));
        }

        List<UserBase> converted = converterFacility.convert(users, UserBase.class);
        assertThat(converted.size()).isEqualTo(2);
        assertThat(converted.stream().map(UserBase::getName)).containsExactlyInAnyOrder("Ana", "Pablo");
    }

    @Test
    public void convert2Test() throws Exception {
        Set<User> users = Sets.newHashSet(createUser("Pablo"), createUser("Ana"));
        User2UserBase user2UserBase = new User2UserBase();
        for(User user : users) {
            Mockito.when(conversionService.convert(user, UserBase.class)).thenReturn(user2UserBase.convert(user));
        }

        Page<? extends User> page = new PageImpl(Lists.newArrayList(users));

        Page<UserBase> converted = converterFacility.convert(page, Pageable.unpaged(), UserBase.class);
        assertThat(converted.getTotalElements()).isEqualTo(2);
        assertThat(converted.getContent().stream().map(UserBase::getName)).containsExactlyInAnyOrder("Ana", "Pablo");

    }

}