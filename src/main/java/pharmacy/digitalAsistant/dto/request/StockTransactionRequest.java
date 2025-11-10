package pharmacy.digitalAsistant.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
import pharmacy.digitalAsistant.domain.enums.TransactionType;

@Data
public class StockTransactionRequest {

    @NotNull(message = "İşlem tipi boş olamaz")
    private TransactionType transactionType;

    @NotNull(message = "Miktar boş olamaz")
    @Min(value = 1, message = "Miktar en az 1 olmalıdır")
    private Integer quantity;

    @NotBlank(message = "İşlemi yapan kişi boş olamaz")
    private String performedBy;

    private String reason;
    private String referenceNumber;
    private String notes;
}