package pharmacy.digitalAsistant.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    /**
     * Create success response with data
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Create success response without message
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return success(data, "İşlem başarılı");
    }

    /**
     * Create success response with only message
     */
    public static ResponseEntity<ApiResponse<Void>> success(String message) {
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                message,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Create created response (201)
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Create error response
     */
    public static ResponseEntity<ApiResponse<Void>> error(String message, HttpStatus status) {
        ApiResponse<Void> response = new ApiResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Create bad request response (400)
     */
    public static ResponseEntity<ApiResponse<Void>> badRequest(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Create not found response (404)
     */
    public static ResponseEntity<ApiResponse<Void>> notFound(String message) {
        return error(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Create unauthorized response (401)
     */
    public static ResponseEntity<ApiResponse<Void>> unauthorized(String message) {
        return error(message, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Create forbidden response (403)
     */
    public static ResponseEntity<ApiResponse<Void>> forbidden(String message) {
        return error(message, HttpStatus.FORBIDDEN);
    }

    /**
     * Create internal server error response (500)
     */
    public static ResponseEntity<ApiResponse<Void>> internalError(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Create validation error response with field errors
     */
    public static ResponseEntity<Map<String, Object>> validationError(Map<String, String> fieldErrors) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Validasyon hatası");
        response.put("errors", fieldErrors);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * API Response wrapper class
     */
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private LocalDateTime timestamp;

        public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.timestamp = timestamp;
        }

        // Getters and setters
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
    }
}