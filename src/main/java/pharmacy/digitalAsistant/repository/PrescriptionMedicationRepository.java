package pharmacy.digitalAsistant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pharmacy.digitalAsistant.domain.entity.PrescriptionMedication;

import java.util.List;

@Repository
public interface PrescriptionMedicationRepository extends JpaRepository<PrescriptionMedication, Long> {

    List<PrescriptionMedication> findByPrescription_Id(Long prescriptionId);

    List<PrescriptionMedication> findByMedication_Id(Long medicationId);
}
