package com.mytasks.user.exception;

/**
 * <p>Base exception to handle cases where the entity is not found or is not available for the user in the DB
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
public class NotFoundException extends RuntimeException {

    private static final String MESSAGE = "'%s' with id '%s' has not being found for tenant '%s'";

    /**
     * <p>Constructs a new not found exception building a default message with
     * the provided attributes.
     * <p>The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param type   Entity type that was not found
     * @param id     Id for the entity
     * @param tenant tenant id of the not found entity
     *
     * @since 1.0.0
     */
    public NotFoundException(String type, String id, String tenant) {
        super(String.format(MESSAGE, type, id, tenant));
    }

    /**
     * <p>Constructs a new not found exception building a default message with
     * the provided attributes.
     * <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param type   Entity type that was not found
     * @param id     Id for the entity
     * @param tenant tenant id of the not found entity
     * @param cause  the cause (which is saved for later retrieval by the
     *               {@link #getCause()} method).  (A <tt>null</tt> value is
     *               permitted, and indicates that the cause is nonexistent or
     *               unknown.)
     *
     * @since 1.0.0
     */
    public NotFoundException(String type, String id, String tenant, Throwable cause) {
        super(String.format(MESSAGE, type, id, tenant), cause);
    }
}
