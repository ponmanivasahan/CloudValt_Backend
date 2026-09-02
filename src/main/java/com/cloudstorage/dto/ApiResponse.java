package com.cloudstorage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

/**
 * Standard API response envelope used by every endpoint.
 *
 * Success:  { success, message, data }
 * Error:    { success, message, errorCode, timestamp, path }
 * Validation: { success, message, data (field→error map), errorCode, timestamp, path }
 *
 * NON_NULL ensures null fields (e.g. errorCode on success) are omitted.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final String  message;
    private final T       data;
    private final String  errorCode;
    private final String  timestamp;
    private final String  path;

    public ApiResponse(boolean success, String message, T data,
                       String errorCode, String timestamp, String path) {
        this.success   = success;
        this.message   = message;
        this.data      = data;
        this.errorCode = errorCode;
        this.timestamp = timestamp != null ? timestamp : (success ? null : Instant.now().toString());
        this.path      = path;
    }

    /** Successful response with a data payload. */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null, null, null);
    }

    /** Successful response with no data (register, logout). */
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null, null, null, null);
    }

    /** Error response. */
    public static <T> ApiResponse<T> error(String message, String errorCode, String path) {
        return new ApiResponse<>(false, message, null, errorCode, Instant.now().toString(), path);
    }

    /** Validation error response with field-level error map. */
    public static ApiResponse<Map<String, String>> validationError(
            String message, Map<String, String> fieldErrors, String path) {
        return new ApiResponse<>(false, message, fieldErrors,
                "VALIDATION_ERROR", Instant.now().toString(), path);
    }
}
