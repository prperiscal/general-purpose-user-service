package com.general.purpose.user.common;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.Map;

import lombok.NoArgsConstructor;

/**
 * <p>Common methods to facilitate the validation.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@NoArgsConstructor(access = PRIVATE)
public final class Validate {

    private static final String MESSAGE = "The validated string '%s' is empty";

    /**
     * <p>Validate that the specified argument is not <code>null</code>;
     * otherwise throwing an exception with the specified message.
     * <p>
     * <pre>Validate.notNull(myObject, "myObject name");</pre>
     *
     * @param object the object to check
     * @param value  the value to append to the message when invalid
     *
     * @since 1.0.0
     */
    public static void notNull(Object object, String value) {
        if(object == null) {
            throw new IllegalArgumentException(format(MESSAGE, value));
        }
    }

    /**
     * <p>Validate that the specified argument array is neither <code>null</code>
     * nor a length of zero (no elements); otherwise throwing an exception
     * with the specified message.
     * <p>
     * <pre>Validate.notEmpty(myArray, "myArray name");</pre>
     *
     * @param array the array to check
     * @param value the value to append to the message when invalid
     *
     * @throws IllegalArgumentException if the array is empty
     * @since 1.0.0
     */
    public static void notEmpty(Object[] array, String value) {
        if(array == null || array.length == 0) {
            throw new IllegalArgumentException(format(MESSAGE, value));
        }
    }

    /**
     * <p>Validate that the specified argument collection is neither <code>null</code>
     * nor a size of zero (no elements); otherwise throwing an exception
     * with the specified message.
     * <p>
     * <pre>Validate.notEmpty(myCollection, "myCollection name");</pre>
     *
     * @param collection the collection to check
     * @param value      the value to append to the message when invalid
     *
     * @throws IllegalArgumentException if the collection is empty
     * @since 1.0.0
     */
    public static void notEmpty(Collection collection, String value) {
        if(collection == null || collection.size() == 0) {
            throw new IllegalArgumentException(format(MESSAGE, value));
        }
    }

    /**
     * <p>Validate that the specified argument map is neither <code>null</code>
     * nor a size of zero (no elements); otherwise throwing an exception
     * with the specified message.
     * <p>
     * <pre>Validate.notEmpty(myMap, "myMap name");</pre>
     *
     * @param map   the map to check
     * @param value the value to append to the message when invalid
     *
     * @throws IllegalArgumentException if the map is empty
     * @since 1.0.0
     */
    public static void notEmpty(Map map, String value) {
        if(map == null || map.size() == 0) {
            throw new IllegalArgumentException(format(MESSAGE, value));
        }
    }

    // notEmpty string
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument string is
     * neither <code>null</code> nor a length of zero (no characters);
     * otherwise throwing an exception with the specified message.
     * <p>
     * <pre>Validate.notEmpty(myString, "myString name");</pre>
     *
     * @param string the string to check
     * @param value  the value to append to the message when invalid
     *
     * @throws IllegalArgumentException if the string is empty
     * @since 1.0.0
     */
    public static void notEmpty(String string, String value) {
        if(string == null || string.length() == 0) {
            throw new IllegalArgumentException(format(MESSAGE, value));
        }
    }

}
