package com.general.purpose.user.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

import com.general.purpose.user.common.Validate;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.NaturalId;

/**
 * <p>Base entity to avoid duplicate Equal hashCode and technical and functional ids.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Setter
    @GeneratedValue
    private long tid;

    /*
     * Based on https://www.clever-cloud.com/blog/engineering/2015/05/20/why-auto-increment-is-a-terrible-idea/ but not taken it all for granted
     * Using UUIDs to expose data to the app has many benefices as explained in the post, but if DB memory for indexes is not a problem, keep the long
     * sequential id is faster when dealing with 'private' joins.
     * In a big environment i would recommend to stick only with uuid as technical ids.
     */
    @NaturalId
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Basic
    @Setter
    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    BaseEntity() {
        this(UUID.randomUUID());
    }

    BaseEntity(UUID id) {
        Validate.notNull(id, "id");
        this.id = id;
    }

    /*
     * Avoid reading all the attributes.
     */
    public String toString() {
        return getClass().getSimpleName() + "(tid=" + tid + ", id=" + id + ")";
    }

    /**
     * <p>Returns a hash code value for the object.
     * <p>Based on Vlad Mihalcea's https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-entity-identifier/
     * <p>Equals and hashCode must behave consistently across all entity state transitions. The problem is that before persisting the entity
     * it has no tid, and after the entity was persisted, the identifier was assigned to a value that was automatically generated, hence the
     * hashCode differs. To avoid this situation Vlad suggest the use of a different constant for each entity. But having track of a large
     * hash base collection to avoid getting different entities with the same hashcode is not practical solution IMHO. Therefore I'll use
     * the natural id that should be unique and not nullable.
     *
     * @since 1.0.0
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    /**
     * <p>Indicates whether some other object is "equal to" this one.
     *
     * @see #hashCode()
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof BaseEntity && new EqualsBuilder().append(getTid(),
                                                                                      ((BaseEntity) obj).getTid())
                                                                              .append(getId(),
                                                                                      ((BaseEntity) obj).getId())
                                                                              .isEquals();
    }

}
