package pharmacy.digitalAsistant.dto.response;



import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InventoryResponse {
    private Long id;
    private Long medicationId;
    private String medicationName;
    private Integer quantity;
    private Integer minimumStockLevel;
    private LocalDate expiryDate;
    private String batchNumber;
    private Double purchasePrice;
    private Double salePrice;
    private boolean lowStock;
    private boolean expired;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}