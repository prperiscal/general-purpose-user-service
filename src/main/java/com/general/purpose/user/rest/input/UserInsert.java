package com.general.purpose.user.rest.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

import com.general.purpose.user.model.Role;
import com.general.purpose.user.model.User;
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

    @NotNull
    private UUID tenantId;

}
