package com.general.purpose.user.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Entity object which represents an {@link User}.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class User extends BaseEntity {

    /*
     * Email is going to be used as login username, therefore it can not be changed
     */
    @Basic
    @Column(nullable = false, updatable = false)
    private String email;

    @Basic
    @Column(nullable = false)
    private String name;

    @Basic
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.WORKER;

    @ManyToMany(mappedBy = "users")
    private Set<UserGroup> userGroups = Sets.newHashSet();

    public User(UUID id) {
        super(id);
    }
}
