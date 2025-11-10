package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy.digitalAsistant.domain.entity.Prescription;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    /**
     * Find prescription by prescription number
     */
    Optional<Prescription> findByPrescriptionNumber(String prescriptionNumber);

    /**
     * Find all prescriptions for a patient
     */
    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId " +
           "ORDER BY p.issueDate DESC")
    List<Prescription> findByPatientId(@Param("patientId") Long patientId);

    /**
     * Find active prescriptions for a patient
     */
    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId " +
           "AND p.isActive = true ORDER BY p.expiryDate ASC")
    List<Prescription> findActiveByPatientId(@Param("patientId") Long patientId);

    /**
     * Find all active prescriptions
     */
    @Query("SELECT p FROM Prescription p WHERE p.isActive = true")
    List<Prescription> findAllActive();

    /**
     * Find prescriptions expiring within date range
     */
    @Query("SELECT p FROM Prescription p WHERE p.expiryDate BETWEEN :startDate AND :endDate " +
           "AND p.isActive = true ORDER BY p.expiryDate ASC")
    List<Prescription> findExpiringBetween(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    /**
     * Find prescriptions expiring within N days
     */
    @Query("SELECT p FROM Prescription p WHERE p.expiryDate <= :targetDate " +
           "AND p.expiryDate >= :now AND p.isActive = true " +
           "ORDER BY p.expiryDate ASC")
    List<Prescription> findExpiringWithinDays(
        @Param("now") LocalDate now,
        @Param("targetDate") LocalDate targetDate
    );

    /**
     * Find expired prescriptions that are still marked as active
     */
    @Query("SELECT p FROM Prescription p WHERE p.expiryDate < :today " +
           "AND p.isActive = true")
    List<Prescription> findExpiredButActive(@Param("today") LocalDate today);

    /**
     * Count active prescriptions
     */
    @Query("SELECT COUNT(p) FROM Prescription p WHERE p.isActive = true")
    long countActivePrescriptions();

    /**
     * Find prescriptions by doctor name
     */
    @Query("SELECT p FROM Prescription p WHERE " +
           "LOWER(p.doctorName) LIKE LOWER(CONCAT('%', :doctorName, '%'))")
    List<Prescription> findByDoctorNameContainingIgnoreCase(@Param("doctorName") String doctorName);

    /**
     * Find prescriptions issued between dates
     */
    @Query("SELECT p FROM Prescription p WHERE p.issueDate BETWEEN :startDate AND :endDate " +
           "ORDER BY p.issueDate DESC")
    List<Prescription> findByIssueDateBetween(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    /**
     * Get prescriptions with medications (eager fetch)
     */
    @Query("SELECT DISTINCT p FROM Prescription p " +
           "LEFT JOIN FETCH p.prescriptionMedications pm " +
           "LEFT JOIN FETCH pm.medication " +
           "WHERE p.id = :prescriptionId")
    Optional<Prescription> findByIdWithMedications(@Param("prescriptionId") Long prescriptionId);
}