package com.general.purpose.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Entity object which represents an {@link UserGroup}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class UserGroup extends BaseEntity {

    @ManyToMany
    private Set<User> users = Sets.newHashSet();

    @Column(length = 127, nullable = false)
    private String name;

    public UserGroup(UUID id) {
        super(id);
    }

}
