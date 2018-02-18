package com.mytasks.user.rest.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

import com.mytasks.user.model.Role;
import com.mytasks.user.model.User;
import lombok.Data;

/**
 * <p>{@link User} insert input.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
public class UserInsert {

    @NotBlank
    @Size(max = 255)
    private String email;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String password;

    @NotNull
    private Role role;

    private UUID tenantId;

}
