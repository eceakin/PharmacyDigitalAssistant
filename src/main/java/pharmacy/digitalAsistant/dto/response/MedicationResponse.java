package pharmacy.digitalAsistant.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MedicationResponse {
    private Long id;
    private String name;
    private String barcode;
    private String serialNumber;
    private String description;
    private String activeIngredient;
    private String manufacturer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}