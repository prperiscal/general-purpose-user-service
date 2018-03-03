package com.general.purpose.user.rest.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.general.purpose.user.model.Role;
import com.general.purpose.user.model.User;
import lombok.Data;

/**
 * <p>{@link User} update input.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
public class UserUpdate {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Role role;

}
