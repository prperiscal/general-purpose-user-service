package com.mytasks.user.rest.input;

import java.util.Set;
import java.util.UUID;

import com.mytasks.user.model.User;
import com.mytasks.user.model.UserGroup;
import lombok.Data;

/**
 * <p>Input for including {@link User users} into {@link UserGroup}.
 *
 * @author Pablo Rey Periscal
 * @since 1.0.0
 */
@Data
public class IncludeUsers {

    private Set<UUID> userIds;
}
