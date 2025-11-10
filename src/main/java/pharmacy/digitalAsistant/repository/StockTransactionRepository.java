package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy.digitalAsistant.domain.entity.StockTransaction;
import pharmacy.digitalAsistant.domain.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    /**
     * Find all transactions for an inventory item
     */
    @Query("SELECT st FROM StockTransaction st WHERE st.inventory.id = :inventoryId " +
           "ORDER BY st.transactionDate DESC")
    List<StockTransaction> findByInventoryId(@Param("inventoryId") Long inventoryId);

    /**
     * Find transactions by type
     */
    List<StockTransaction> findByTransactionType(TransactionType transactionType);

    /**
     * Find transactions performed by a specific person
     */
    @Query("SELECT st FROM StockTransaction st WHERE " +
           "LOWER(st.performedBy) LIKE LOWER(CONCAT('%', :performedBy, '%')) " +
           "ORDER BY st.transactionDate DESC")
    List<StockTransaction> findByPerformedByContainingIgnoreCase(@Param("performedBy") String performedBy);

    /**
     * Find transactions between dates
     */
    @Query("SELECT st FROM StockTransaction st WHERE " +
           "st.transactionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY st.transactionDate DESC")
    List<StockTransaction> findByTransactionDateBetween(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /**
     * Find transactions for a medication
     */
    @Query("SELECT st FROM StockTransaction st WHERE " +
           "st.inventory.medication.id = :medicationId " +
           "ORDER BY st.transactionDate DESC")
    List<StockTransaction> findByMedicationId(@Param("medicationId") Long medicationId);

    /**
     * Find recent transactions (last N days)
     */
    @Query("SELECT st FROM StockTransaction st WHERE " +
           "st.transactionDate >= :sinceDate " +
           "ORDER BY st.transactionDate DESC")
    List<StockTransaction> findRecentTransactions(@Param("sinceDate") LocalDateTime sinceDate);

    /**
     * Calculate total IN quantity for an inventory item
     */
    @Query("SELECT SUM(st.quantity) FROM StockTransaction st WHERE " +
           "st.inventory.id = :inventoryId AND st.transactionType = 'IN'")
    Integer calculateTotalInQuantity(@Param("inventoryId") Long inventoryId);

    /**
     * Calculate total OUT quantity for an inventory item
     */
    @Query("SELECT SUM(st.quantity) FROM StockTransaction st WHERE " +
           "st.inventory.id = :inventoryId AND st.transactionType = 'OUT'")
    Integer calculateTotalOutQuantity(@Param("inventoryId") Long inventoryId);

    /**
     * Count transactions by type in date range
     */
    @Query("SELECT COUNT(st) FROM StockTransaction st WHERE " +
           "st.transactionType = :type AND " +
           "st.transactionDate BETWEEN :startDate AND :endDate")
    long countByTypeInDateRange(
        @Param("type") TransactionType type,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /**
     * Find last N transactions
     */
    @Query("SELECT st FROM StockTransaction st ORDER BY st.transactionDate DESC")
    List<StockTransaction> findLatestTransactions();
}