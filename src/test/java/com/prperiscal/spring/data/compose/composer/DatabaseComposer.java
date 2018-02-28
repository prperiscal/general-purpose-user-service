package com.prperiscal.spring.data.compose.composer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.prperiscal.spring.data.compose.exception.GenericComposeException;
import com.prperiscal.spring.data.compose.model.ComposeData;
import com.prperiscal.spring.data.compose.model.ComposeMetaData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Maps;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.prperiscal.spring.data.compose.composer.ComposerUtils.getEntityClass;
import static com.prperiscal.spring.data.compose.composer.ComposerUtils.getResource;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@Component
@RequiredArgsConstructor
public class DatabaseComposer {

    private static final String FK_CHAR = "@";

    @PersistenceContext
    @NonNull
    private final EntityManager entityManager;

    @NonNull
    private final ApplicationContext appContext;

    @Modifying
    @Transactional
    public void compose(Class<?> testClass, String composeResource) throws IOException {
        URL resourcePath = getResource(testClass, composeResource);

        ObjectMapper objectMapper = new ObjectMapper();
        ComposeData composeData = objectMapper.readValue(resourcePath, ComposeData.class);

        //TODO validate composeData, each key in entities should have a metadaData object associated

        importResource(composeData);
    }

    private void importResource(ComposeData composeData) {
        Map<String, Map<String, List<Object>>> insertedEntities = composeData.getEntities().entrySet().stream().map(
                entry -> processEntityGroup(entry.getValue(), composeData.getMetadata().get(entry.getKey()))).flatMap(
                map -> map.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        createRelations(composeData, insertedEntities);
    }

    private void createRelations(ComposeData composeData, Map<String, Map<String, List<Object>>> insertedEntities) {
        composeData.getEntities().forEach(
                (key, value) -> joinEntityGroup(value, composeData.getMetadata().get(key), insertedEntities));
    }

    private void joinEntityGroup(List<Map<String, Object>> entitiesData, ComposeMetaData composeMetaData,
            Map<String, Map<String, List<Object>>> insertedEntities) {
        for (Map<String, Object> entityData : entitiesData) {
            entityData.entrySet().stream().filter(entry -> entry.getValue().toString().startsWith(FK_CHAR)).forEach(
                    entry -> joinEntity(entry, entityData, composeMetaData, insertedEntities));
        }
        entityManager.flush();
    }

    private void joinEntity(Map.Entry<String, Object> entry, Map<String, Object> entityData,
            ComposeMetaData composeMetaData, Map<String, Map<String, List<Object>>> entitiesData) {

        String parseredFiled = entry.getKey().substring(1);
        entitiesData.get(composeMetaData.get_class()).get()

    }

    private Map<String, List<Object>> processEntityGroup(List<Map<String, Object>> entitiesData,
            ComposeMetaData composeMetaData) {
        List<Object> insertedEntities = Lists.newArrayList();
        for (Map<String, Object> entityData : entitiesData) {
            insertedEntities.add(insertEntity(composeMetaData, entityData));
        }
        entityManager.flush();

        return Maps.newHashMap(composeMetaData.get_class(), insertedEntities);
    }

    private Object insertEntity(ComposeMetaData composeMetaData, Map<String, Object> entityResource) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Object entity = objectMapper.convertValue(entityResource, getEntityClass(composeMetaData.get_class()));
            CrudRepository repository = (CrudRepository) appContext.getBean(composeMetaData.getRepository());
            return repository.save(entity);
        } catch (ClassNotFoundException e) {
            throw new GenericComposeException();
        }
    }

}
