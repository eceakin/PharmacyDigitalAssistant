package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy.digitalAsistant.domain.entity.PrescriptionMedication;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionMedicationRepository extends JpaRepository<PrescriptionMedication, Long> {

    /**
     * Find all medications for a prescription
     */
    @Query("SELECT pm FROM PrescriptionMedication pm WHERE pm.prescription.id = :prescriptionId")
    List<PrescriptionMedication> findByPrescriptionId(@Param("prescriptionId") Long prescriptionId);

    /**
     * Find all prescriptions for a medication
     */
    @Query("SELECT pm FROM PrescriptionMedication pm WHERE pm.medication.id = :medicationId")
    List<PrescriptionMedication> findByMedicationId(@Param("medicationId") Long medicationId);

    /**
     * Find active prescription medications
     */
    @Query("SELECT pm FROM PrescriptionMedication pm WHERE " +
           "pm.startDate <= :today AND pm.endDate >= :today")
    List<PrescriptionMedication> findActiveMedications(@Param("today") LocalDate today);

    /**
     * Find prescription medications ending within N days
     */
    @Query("SELECT pm FROM PrescriptionMedication pm WHERE " +
           "pm.endDate BETWEEN :startDate AND :endDate " +
           "ORDER BY pm.endDate ASC")
    List<PrescriptionMedication> findEndingBetween(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    /**
     * Find prescription medications ending soon for a patient
     */
    @Query("SELECT pm FROM PrescriptionMedication pm " +
           "WHERE pm.prescription.patient.id = :patientId " +
           "AND pm.endDate BETWEEN :startDate AND :endDate " +
           "ORDER BY pm.endDate ASC")
    List<PrescriptionMedication> findEndingSoonForPatient(
        @Param("patientId") Long patientId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    /**
     * Find medications for a specific patient
     */
    @Query("SELECT pm FROM PrescriptionMedication pm " +
           "WHERE pm.prescription.patient.id = :patientId " +
           "ORDER BY pm.endDate DESC")
    List<PrescriptionMedication> findByPatientId(@Param("patientId") Long patientId);

    /**
     * Count active prescription medications for a patient
     */
    @Query("SELECT COUNT(pm) FROM PrescriptionMedication pm " +
           "WHERE pm.prescription.patient.id = :patientId " +
           "AND pm.startDate <= :today AND pm.endDate >= :today")
    long countActiveMedicationsForPatient(
        @Param("patientId") Long patientId,
        @Param("today") LocalDate today
    );
}