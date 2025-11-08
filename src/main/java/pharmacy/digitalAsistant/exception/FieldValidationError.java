package pharmacy.digitalAsistant.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldValidationError {
    private String field;
    private Object rejectedValue;
    private String message;
}
