package pharmacy.digitalAsistant.exception;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiError {
    private OffsetDateTime timestamp;
    private int status;
    private String error;            // e.g., "Bad Request"
    private String message;          // summary message
    private String path;             // request path
    private List<FieldValidationError> errors; // optional field-level details
}
