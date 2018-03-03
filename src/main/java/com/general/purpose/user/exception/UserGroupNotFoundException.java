package com.general.purpose.user.exception;

/**
 * <p>Exception to handle cases where the userGroup object is not found or is not available for the user in the DB
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
public class UserGroupNotFoundException extends NotFoundException {

    private static final String TYPE = "UserGroup";

    /**
     * <p>Constructs a new User not found exception building a default message with
     * the provided attributes.
     * <p>The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param id     Id for the entity
     * @param tenant tenant id of the not found entity
     *
     * @since 1.0.0
     */
    public UserGroupNotFoundException(String id, String tenant) {
        super(TYPE, id, tenant);
    }

    /**
     * <p>Constructs a new User not found exception building a default message with
     * the provided attributes.
     * <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param id     Id for the entity
     * @param tenant tenant id of the not found entity
     * @param cause  the cause (which is saved for later retrieval by the
     *               {@link #getCause()} method).  (A <tt>null</tt> value is
     *               permitted, and indicates that the cause is nonexistent or
     *               unknown.)
     *
     * @since 1.0.0
     */
    public UserGroupNotFoundException(String id, String tenant, Throwable cause) {
        super(TYPE, id, tenant, cause);
    }
}
