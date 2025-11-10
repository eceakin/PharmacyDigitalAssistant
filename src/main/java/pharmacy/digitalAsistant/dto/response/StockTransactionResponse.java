package pharmacy.digitalAsistant.dto.response;


import lombok.Data;
import pharmacy.digitalAsistant.domain.enums.TransactionType;
import java.time.LocalDateTime;

@Data
public class StockTransactionResponse {
    private Long id;
    private Long inventoryId;
    private String medicationName;
    private TransactionType transactionType;
    private Integer quantity;
    private String performedBy;
    private String reason;
    private String referenceNumber;
    private String notes;
    private LocalDateTime transactionDate;
}
