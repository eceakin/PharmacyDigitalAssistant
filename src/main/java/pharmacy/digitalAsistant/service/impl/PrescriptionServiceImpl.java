package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.domain.entity.Medication;
import pharmacy.digitalAsistant.domain.entity.Patient;
import pharmacy.digitalAsistant.domain.entity.Prescription;
import pharmacy.digitalAsistant.domain.entity.PrescriptionMedication;
import pharmacy.digitalAsistant.dto.mapper.PrescriptionMapper;
import pharmacy.digitalAsistant.dto.mapper.PrescriptionMedicationMapper;
import pharmacy.digitalAsistant.dto.request.PrescriptionMedicationCreateDTO;
import pharmacy.digitalAsistant.dto.request.PrescriptionRequestDTO;
import pharmacy.digitalAsistant.dto.response.PrescriptionResponseDTO;
import pharmacy.digitalAsistant.repository.MedicationRepository;
import pharmacy.digitalAsistant.repository.PatientRepository;
import pharmacy.digitalAsistant.repository.PrescriptionMedicationRepository;
import pharmacy.digitalAsistant.repository.PrescriptionRepository;
import pharmacy.digitalAsistant.service.abstracts.PrescriptionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final PrescriptionMedicationMapper pmMapper;
    private final PrescriptionMedicationRepository pmRepository;

    @Transactional
    @Override
    public PrescriptionResponseDTO create(PrescriptionRequestDTO dto) {

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setPrescriberName(dto.getPrescriberName());
        prescription.setIssueDate(dto.getIssueDate());
        prescription.setExpiryDate(dto.getExpiryDate());

        Prescription saved = prescriptionRepository.save(prescription);

        // add medications
        if (dto.getMedications() != null) {
            for (PrescriptionMedicationCreateDTO m : dto.getMedications()) {
                Medication med = medicationRepository.findById(m.getMedicationId())
                        .orElseThrow(() -> new RuntimeException("Medication not found"));

                PrescriptionMedication pm = pmMapper.toEntity(m);
                pm.setPrescription(saved);
                pm.setMedication(med);
                pmRepository.save(pm);
            }
        }

        return prescriptionMapper.toDTO(saved);
    }

    @Override
    public PrescriptionResponseDTO get(Long id) {
        return prescriptionMapper.toDTO(
                prescriptionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Prescription not found")));
    }

    @Override
    public List<PrescriptionResponseDTO> getByPatient(Long patientId) {
        return prescriptionRepository.findByPatient_Id(patientId).stream()
                .map(prescriptionMapper::toDTO)
                .toList();
    }
}
