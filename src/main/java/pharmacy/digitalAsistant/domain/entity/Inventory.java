package pharmacy.digitalAsistant.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory", indexes = {
    @Index(name = "idx_expiry_date", columnList = "expiry_date"),
    @Index(name = "idx_medication_id", columnList = "medication_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "minimum_stock_level", nullable = false)
    private Integer minimumStockLevel = 10;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "batch_number", length = 100)
    private String batchNumber;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "sale_price")
    private Double salePrice;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockTransaction> stockTransactions = new ArrayList<>();

    // Helper method to check if stock is low
    public boolean isLowStock() {
        return quantity <= minimumStockLevel;
    }

    // Helper method to check if expiring soon (within months)
    public boolean isExpiringSoon(int months) {
        LocalDate targetDate = LocalDate.now().plusMonths(months);
        return expiryDate.isBefore(targetDate) || expiryDate.isEqual(targetDate);
    }

    // Helper method to check if expired
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    // Helper method to add stock
    public void addStock(int amount) {
        this.quantity += amount;
    }

    // Helper method to remove stock
    public void removeStock(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient stock. Available: " + this.quantity);
        }
    }
}