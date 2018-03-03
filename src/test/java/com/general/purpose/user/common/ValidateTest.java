package com.general.purpose.user.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

public class ValidateTest {

    @Test
    public void notNullStringTest() {
        Object object = "notNull";
        Validate.notNull(object, "string");
        assertThat(object).isEqualTo("notNull");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notNullNullTest() {
        Validate.notNull(null, "nullObject");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmptyArrayTest() {
        Integer[] integers = new Integer[]{};
        Validate.notEmpty(integers, "integers");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmptyCollectionTest() {
        Set set = Sets.newSet();
        Validate.notEmpty(set, "set");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmptyCollectionNullTest() {
        Set set = null;
        Validate.notEmpty(set, "collection");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmptyMapTest() {
        Map map = Maps.newHashMap();
        Validate.notEmpty(map, "map");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmptyMapNullTest() {
        Map map = null;
        Validate.notEmpty(map, "map");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmptyStringTest() {
        String string = "";
        Validate.notEmpty(string, "string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmptyStringNullTest() {
        String string = null;
        Validate.notEmpty(string, "string");
    }
}
