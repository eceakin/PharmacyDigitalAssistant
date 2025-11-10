package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy.digitalAsistant.domain.entity.Inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Find inventory by medication ID
     */
    @Query("SELECT i FROM Inventory i WHERE i.medication.id = :medicationId")
    List<Inventory> findByMedicationId(@Param("medicationId") Long medicationId);

    /**
     * Find low stock items (quantity <= minimum stock level)
     */
    @Query("SELECT i FROM Inventory i WHERE i.quantity <= i.minimumStockLevel")
    List<Inventory> findLowStockItems();

    /**
     * Find items expiring within specific date range
     */
    @Query("SELECT i FROM Inventory i WHERE i.expiryDate BETWEEN :startDate AND :endDate " +
           "ORDER BY i.expiryDate ASC")
    List<Inventory> findByExpiryDateBetween(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );

    /**
     * Find items expiring within N months
     */
    @Query("SELECT i FROM Inventory i WHERE i.expiryDate <= :targetDate " +
           "AND i.expiryDate >= :now ORDER BY i.expiryDate ASC")
    List<Inventory> findExpiringWithinMonths(
        @Param("now") LocalDate now,
        @Param("targetDate") LocalDate targetDate
    );

    /**
     * Find expired items
     */
    @Query("SELECT i FROM Inventory i WHERE i.expiryDate < :today")
    List<Inventory> findExpiredItems(@Param("today") LocalDate today);

    /**
     * Find inventory by batch number
     */
    Optional<Inventory> findByBatchNumber(String batchNumber);

    /**
     * Get total inventory count
     */
    @Query("SELECT COUNT(i) FROM Inventory i")
    long countTotalInventoryItems();

    /**
     * Get count of low stock items
     */
    @Query("SELECT COUNT(i) FROM Inventory i WHERE i.quantity <= i.minimumStockLevel")
    long countLowStockItems();

    /**
     * Get total stock value
     */
    @Query("SELECT SUM(i.quantity * i.purchasePrice) FROM Inventory i")
    Double getTotalStockValue();

    /**
     * Find all inventory with medication details (eager fetch)
     */
    @Query("SELECT i FROM Inventory i " +
           "JOIN FETCH i.medication " +
           "ORDER BY i.expiryDate ASC")
    List<Inventory> findAllWithMedication();

    /**
     * Find inventory items with quantity greater than zero
     */
    @Query("SELECT i FROM Inventory i WHERE i.quantity > 0")
    List<Inventory> findAvailableStock();
}