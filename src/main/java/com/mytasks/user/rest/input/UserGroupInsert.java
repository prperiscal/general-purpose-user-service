package com.mytasks.user.rest.input;

import com.mytasks.user.model.UserGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * <p>{@link UserGroup} insert input.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
public class UserGroupInsert {

    @NotEmpty
    @Size(max = 127)
    private String name;

    @NotNull
    private UUID tenantId;
}
