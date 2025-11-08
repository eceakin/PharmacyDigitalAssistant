package pharmacy.digitalAsistant.domain.entity;


import lombok.*;
import pharmacy.digitalAsistant.domain.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Min(1)
    private Integer quantity;

    private Long performedBy; // user id who performed

    private LocalDateTime transactionDate;
}
