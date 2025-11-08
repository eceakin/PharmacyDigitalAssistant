package pharmacy.digitalAsistant.dto.response;


import lombok.Data;
import java.time.LocalDate;

@Data
public class InventoryResponseDTO {
    private Long id;
    private MedicationResponseDTO medication;
    private Integer quantity;
    private Integer minimumStockLevel;
    private String lotNumber;
    private LocalDate expiryDate;
    private String location;
}

