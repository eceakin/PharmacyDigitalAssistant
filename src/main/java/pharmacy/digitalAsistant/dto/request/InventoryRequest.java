package pharmacy.digitalAsistant.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class InventoryRequest {

    @NotNull(message = "İlaç ID boş olamaz")
    private Long medicationId;

    @NotNull(message = "Miktar boş olamaz")
    @Min(value = 0, message = "Miktar 0'dan küçük olamaz")
    private Integer quantity;

    @NotNull(message = "Minimum stok seviyesi boş olamaz")
    @Min(value = 1, message = "Minimum stok seviyesi en az 1 olmalıdır")
    private Integer minimumStockLevel;

    @NotNull(message = "Son kullanma tarihi boş olamaz")
    @Future(message = "Son kullanma tarihi gelecekte olmalıdır")
    private LocalDate expiryDate;

    private String batchNumber;
    private Double purchasePrice;
    private Double salePrice;
    private String performedBy;
}