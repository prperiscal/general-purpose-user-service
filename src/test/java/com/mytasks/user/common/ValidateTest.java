package com.mytasks.user.common;

import org.junit.Test;

public class ValidateTest {

    @Test
    public void notNullStringTest() {
        String string = "notNull";
        Validate.notNull(string,"string");
    }


    @Test(expected = IllegalArgumentException.class)
    public void notNullNullTest() {
        Validate.notNull(null,"nullObject");
    }


    @Test
    public void notEmpty() {
    }

    @Test
    public void notEmpty1() {
    }

    @Test
    public void notEmpty2() {
    }

    @Test
    public void notEmpty3() {
    }
}
