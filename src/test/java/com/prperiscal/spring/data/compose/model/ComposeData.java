package com.prperiscal.spring.data.compose.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author Pablo Rey Periscal
 */
@Data
@NoArgsConstructor
public class ComposeData {

    @NonNull
    private Map<String,ComposeMetaData> metadata;

    @NotNull
    private Map<String, List<Map<String,Object>>> entities;
}
