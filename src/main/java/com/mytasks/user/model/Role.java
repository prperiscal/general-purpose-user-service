package com.mytasks.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Represents available roles.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum Role {

    WORKER("Worker", 10), LEADER("Leader", 20), DIRECTOR("Director", 30), ADMINISTRATOR("Administrator", 40);

    private String name;
    private int accessLevel;
}
