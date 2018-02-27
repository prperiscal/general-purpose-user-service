package com.prperiscal.spring.data.compòse;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Transactional
@TestExecutionListeners(listeners = {DataComposeExecutionListener.class}, mergeMode = MERGE_WITH_DEFAULTS)
public @interface SpringDataCompose {

    /**
     * <p>The value indicates the basic name or the full path to the resource for loading, which should conform the json
     * structure to load data into database.
     *
     * @return the basic name or full path.
     */
    String value() default "";
}
