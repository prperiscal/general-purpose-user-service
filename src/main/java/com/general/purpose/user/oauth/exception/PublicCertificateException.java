package com.general.purpose.user.oauth.exception;

/**
 * <p>Exceptions related with public certificate.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
public class PublicCertificateException extends RuntimeException {

    /**
     * Constructs a new Public Certificate exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public PublicCertificateException() {
        super();
    }

    /**
     * Constructs a new Public Certificate exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public PublicCertificateException(String message) {
        super(message);
    }

    /**
     * Constructs a new Public Certificate exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     *
     * @since 1.4
     */
    public PublicCertificateException(String message, Throwable cause) {
        super(message, cause);
    }
}
