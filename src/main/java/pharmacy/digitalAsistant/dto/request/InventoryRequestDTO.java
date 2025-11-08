package pharmacy.digitalAsistant.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InventoryRequestDTO {
    private Long medicationId;
    private Integer quantity;
    private Integer minimumStockLevel;
    private String lotNumber;
    private LocalDate expiryDate;
    private String location;
}
