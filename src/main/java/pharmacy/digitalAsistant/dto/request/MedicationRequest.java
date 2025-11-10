package pharmacy.digitalAsistant.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MedicationRequest {
    @NotBlank(message = "İlaç adı boş olamaz")
    @Size(min = 2, max = 200, message = "İlaç adı 2-200 karakter arası olmalıdır")
    private String name;

    private String barcode;
    private String serialNumber;
    private String description;
    private String activeIngredient;
    private String manufacturer;
}
