package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy.digitalAsistant.domain.entity.Patient;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Find patient by TC Kimlik number
     */
    Optional<Patient> findByTcKimlikNo(String tcKimlikNo);

    /**
     * Check if TC Kimlik already exists
     */
    boolean existsByTcKimlikNo(String tcKimlikNo);

    /**
     * Find patient by phone number
     */
    Optional<Patient> findByPhone(String phone);

    /**
     * Search patients by name (case-insensitive)
     */
    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Patient> searchByName(@Param("searchTerm") String searchTerm);

    /**
     * Search patients by name or TC Kimlik
     */
    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "p.tcKimlikNo LIKE CONCAT('%', :searchTerm, '%')")
    List<Patient> searchPatients(@Param("searchTerm") String searchTerm);

    /**
     * Find patients with chronic diseases
     */
    @Query("SELECT p FROM Patient p WHERE p.chronicDiseases IS NOT NULL AND p.chronicDiseases != ''")
    List<Patient> findPatientsWithChronicDiseases();

    /**
     * Find patients with active prescriptions
     */
    @Query("SELECT DISTINCT p FROM Patient p " +
           "JOIN p.prescriptions pr " +
           "WHERE pr.isActive = true")
    List<Patient> findPatientsWithActivePrescriptions();

    /**
     * Count total patients
     */
    @Query("SELECT COUNT(p) FROM Patient p")
    long countTotalPatients();
}