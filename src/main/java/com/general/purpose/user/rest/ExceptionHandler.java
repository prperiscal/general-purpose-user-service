package com.general.purpose.user.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Map;

import com.general.purpose.user.exception.UserGroupNotFoundException;
import com.general.purpose.user.exception.UserNotFoundException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * <p>Gets the exceptions from Controllers and map them to {@link HttpStatus} codes./p>
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    private static final String RESPONSE_TIMESTAMP = "timestamp";
    private static final String RESPONSE_EXCEPTION = "exception";
    private static final String RESPONSE_MESSAGE = "message";
    private static final String RESPONSE_STATUS = "status";
    private static final String RESPONSE_ERROR = "error";
    private static final String RESPONSE_PATH = "path";

    /**
     * <p>Defines which {@link Exception}s will be mapped to {@link HttpStatus} code 400 [BAD_REQUEST].
     *
     * @param cause      the exception that was thrown
     * @param webRequest the request
     *
     * @return {@link ResponseEntity} with the proper status
     * @since 1.0.0
     */
    @ResponseStatus(code = BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({NullPointerException.class,
                                                               IllegalArgumentException.class})
    public ResponseEntity<?> badRequest(Exception cause, WebRequest webRequest) {
        return handleException(BAD_REQUEST, cause, webRequest);
    }

    /**
     * <p>Defines which {@link Exception}s will be mapped to {@link HttpStatus} code 500 [INTERNAL_SERVER_ERROR].
     *
     * @param cause      the exception that was thrown
     * @param webRequest the request
     *
     * @return {@link ResponseEntity} with the proper status
     * @since 1.0.0
     */
    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler({DataAccessException.class, IllegalStateException.class})
    public ResponseEntity<?> internalServerError(Exception cause, WebRequest webRequest) {
        return handleException(INTERNAL_SERVER_ERROR, cause, webRequest);
    }

    /**
     * <p>Defines which {@link Exception}s will be mapped to {@link HttpStatus} code 404 [NOT_FOUND].
     *
     * @param cause      the exception that was thrown
     * @param webRequest the request
     *
     * @return {@link ResponseEntity} with the proper status
     * @since 1.0.0
     */
    @ResponseStatus(code = NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler({UserNotFoundException.class,
                                                               UserGroupNotFoundException.class})
    public ResponseEntity<?> notFound(Exception cause, WebRequest webRequest) {
        return handleException(NOT_FOUND, cause, webRequest);
    }

    private static ResponseEntity<Map<String, Object>> handleException(HttpStatus httpStatus, Exception cause,
                                                                       WebRequest webRequest) {
        Map<String, Object> response = Maps.newHashMap();
        response.put(RESPONSE_MESSAGE, cause.getMessage());
        response.put(RESPONSE_ERROR, httpStatus.getReasonPhrase());
        response.put(RESPONSE_STATUS, httpStatus.value());
        response.put(RESPONSE_EXCEPTION, cause.getClass().getName());
        response.put(RESPONSE_TIMESTAMP, System.currentTimeMillis());

        String path = extractPath(webRequest);
        response.put(RESPONSE_PATH, path);
        return ResponseEntity.status(httpStatus).body(response);
    }

    private static String extractPath(WebRequest webRequest) {
        if(webRequest == null) {
            return null;
        }
        ServletWebRequest swr = (ServletWebRequest) webRequest;
        String description = swr.getDescription(false);
        return StringUtils.substringAfter(description, "uri=");
    }
}
