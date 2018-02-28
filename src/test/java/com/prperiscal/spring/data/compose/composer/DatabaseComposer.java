package com.prperiscal.spring.data.compose.composer;

import static com.prperiscal.spring.data.compose.composer.ComposerUtils.getEntityClass;
import static com.prperiscal.spring.data.compose.composer.ComposerUtils.getResource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prperiscal.spring.data.compose.exception.GenericComposeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@Component
@RequiredArgsConstructor
public class DatabaseComposer {

    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ApplicationContext appContext;

    @Modifying
    @Transactional
    public void compose(Class<?> testClass, String composeResource) throws IOException {
        URL resourcePath = getResource(testClass, composeResource);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Map<String, Object>>> resourceMap = objectMapper
                .readValue(resourcePath, new TypeReference<Map<String, List<Map<String, Object>>>>() {
                });

        importResource(resourceMap);
    }

    private void importResource(Map<String, List<Map<String, Object>>> resourceMap) {
        resourceMap.entrySet().forEach(this::processEntityGroup);
    }

    private void processEntityGroup(Map.Entry<String, List<Map<String, Object>>> entityGroup) {
        String key = entityGroup.getKey();
        List<Map<String, Object>> entitiesResource = entityGroup.getValue();

        for(Map<String, Object> entityResource : entitiesResource) {
            importEntity(key, entityResource);
        }
        entityManager.flush();
    }

    private void importEntity(String key, Map<String, Object> entityResource) {
        String className = (String) entityResource.get("_class");
        entityResource.remove("_class");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Object entity = objectMapper.convertValue(entityResource, getEntityClass(className));
            CrudRepository repository = (CrudRepository) appContext.getBean(key.concat("Repository"));
            repository.save(entity);
        } catch (ClassNotFoundException e) {
            throw new GenericComposeException();
        }
    }

}
