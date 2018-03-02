package com.mytasks.user.facility;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <p>Facility to help converting objects using {@link ConversionService}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ConverterFacility {

    @NonNull
    private ConversionService conversionService;

    /**
     * <p>Converts the given sources to the projection specified.
     *
     * @param page       {@link Page} of objects to convert
     * @param pageable   page properties
     * @param targetType projection type
     *
     * @return {@link Page} with all converted objects
     * @since 1.0.0
     */
    public <S, T> Page<T> convert(Page<? extends S> page, Pageable pageable, Class<T> targetType) {
        final List<T> content = convert(page.getContent(), targetType);
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }


    /**
     * <p>Converts the given sources to the projection specified.
     *
     * @param sources objects to convert
     * @param targetType projection type
     * @return {@link List} with all converted objects
     */
    public <S, T> List<T> convert(Collection<? extends S> sources, Class<T> targetType) {
        return sources.stream().map((source) -> conversionService.convert(source, targetType))
                      .collect(Collectors.toList());
    }

    /**
     * <p>Converts the given source to the projection specified.
     *
     * @param source     Object to convert
     * @param targetType Projection to convert to
     *
     * @return The converted object with the targetType format
     * @since 1.0.0
     */
    public <S, T> T convert(S source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }

}
