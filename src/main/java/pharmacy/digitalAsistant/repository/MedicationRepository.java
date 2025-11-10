package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy.digitalAsistant.domain.entity.Medication;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    /**
     * Find medication by barcode
     */
    Optional<Medication> findByBarcode(String barcode);

    /**
     * Check if barcode already exists
     */
    boolean existsByBarcode(String barcode);

    /**
     * Search medications by name (case-insensitive)
     */
    @Query("SELECT m FROM Medication m WHERE " +
           "LOWER(m.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Medication> searchByName(@Param("searchTerm") String searchTerm);

    /**
     * Search medications by name or barcode
     */
    @Query("SELECT m FROM Medication m WHERE " +
           "LOWER(m.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "m.barcode LIKE CONCAT('%', :searchTerm, '%')")
    List<Medication> searchMedications(@Param("searchTerm") String searchTerm);

    /**
     * Find medications by manufacturer
     */
    List<Medication> findByManufacturerContainingIgnoreCase(String manufacturer);

    /**
     * Find medications by active ingredient
     */
    List<Medication> findByActiveIngredientContainingIgnoreCase(String activeIngredient);

    /**
     * Get all medications ordered by name
     */
    List<Medication> findAllByOrderByNameAsc();

    /**
     * Count total medications
     */
    @Query("SELECT COUNT(m) FROM Medication m")
    long countTotalMedications();
}