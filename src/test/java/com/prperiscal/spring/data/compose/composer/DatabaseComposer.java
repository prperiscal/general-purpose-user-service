package com.prperiscal.spring.data.compose.composer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prperiscal.spring.data.compose.exception.FileInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.replace;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@Component
@RequiredArgsConstructor
public class DatabaseComposer {

    private static final String FILE_EXTENSION_JSON = "json";

    private final ResourceLoader resourceLoader;

    public void compose(Class<?> testClass, String composeResource) throws IOException {
        File file =  getResource(testClass, composeResource);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<?,?> empMap = objectMapper.readValue(file, Map.class);

        System.out.println("breakpoint");
    }

    private File getResource(Class<?> testClass, String composeResource) throws FileNotFoundException {
        Long dots = composeResource.chars().filter(character -> character == '.').count();
        String packagePath = testClass.getPackage().getName();
        String path;
        if(dots == 1){
            if(composeResource.endsWith("." + FILE_EXTENSION_JSON)){
                path = "/".concat(replace(packagePath, ".", "/").concat("/").concat(composeResource));
            }else{
                throw new FileInvalidException(composeResource);
            }
        }else if(dots > 1){
            path = replace(composeResource, ".", "/");
        }else{
            path = "/".concat(replace(packagePath, ".", "/").concat("/").concat(composeResource).concat(FILE_EXTENSION_JSON));
        }

        return new File(testClass.getResource(path).getPath());
    }

}
