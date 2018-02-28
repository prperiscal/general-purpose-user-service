package com.prperiscal.spring.data.compose.composer;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.replace;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URL;

import com.prperiscal.spring.data.compose.exception.FileInvalidException;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@NoArgsConstructor(access = PRIVATE)
final class ComposerUtils {

    private static final String FILE_EXTENSION_JSON = "json";

    static Class<?> getEntityClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    static URL getResource(Class<?> testClass, String composeResource) throws FileNotFoundException {
        Long dots = composeResource.chars().filter(character -> character == '.').count();
        String packagePath = testClass.getPackage().getName();
        String path;

        if(dots == 1) {
            if(composeResource.endsWith("." + FILE_EXTENSION_JSON)) {
                path = getValidPackagePath(packagePath).concat(composeResource);
            } else {
                throw new FileInvalidException(composeResource);
            }
        } else if(dots > 1) {
            path = replace(composeResource, ".", "/");
        } else {
            path = getValidPackagePath(packagePath).concat(composeResource).concat(FILE_EXTENSION_JSON);
        }

        return testClass.getResource(path);
    }

    static String getValidPackagePath(String packagePath) {
        return "/".concat(replace(packagePath, ".", "/").concat("/"));
    }

    static Method getGetter(Object object, String fieldName) throws NoSuchMethodException {
        return object.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
    }

}
