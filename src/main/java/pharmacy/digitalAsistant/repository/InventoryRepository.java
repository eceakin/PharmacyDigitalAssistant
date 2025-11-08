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

    @Query("SELECT i FROM Inventory i WHERE i.quantity <= i.minimumStockLevel")
    List<Inventory> findLowStockItems();

    @Query("SELECT i FROM Inventory i WHERE i.expiryDate BETWEEN :startDate AND :endDate")
    List<Inventory> findItemsExpiringBetween(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT i FROM Inventory i WHERE i.medication.id = :medicationId")
    Optional<Inventory> findByMedicationId(@Param("medicationId") Long medicationId);

    List<Inventory> findByMedication_Id(Long medicationId);
}
