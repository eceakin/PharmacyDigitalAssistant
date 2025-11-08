package pharmacy.digitalAsistant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import pharmacy.digitalAsistant.domain.entity.StockTransaction;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByInventory_Id(Long inventoryId);

    @Query("SELECT s FROM StockTransaction s WHERE s.transactionDate BETWEEN :from AND :to")
    List<StockTransaction> findTransactionsBetween(@Param("from") LocalDateTime from,
                                                   @Param("to") LocalDateTime to);

}
