package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacy.digitalAsistant.domain.entity.Medication;
import pharmacy.digitalAsistant.domain.entity.Patient;
import pharmacy.digitalAsistant.domain.entity.Prescription;
import pharmacy.digitalAsistant.domain.entity.PrescriptionMedication;
import pharmacy.digitalAsistant.dto.request.PrescriptionMedicationRequest;
import pharmacy.digitalAsistant.dto.request.PrescriptionRequest;
import pharmacy.digitalAsistant.dto.response.PrescriptionMedicationResponse;
import pharmacy.digitalAsistant.dto.response.PrescriptionResponse;
import pharmacy.digitalAsistant.exception.ResourceNotFoundException;
import pharmacy.digitalAsistant.repository.MedicationRepository;
import pharmacy.digitalAsistant.repository.PatientRepository;
import pharmacy.digitalAsistant.repository.PrescriptionRepository;
import pharmacy.digitalAsistant.service.abstracts.PrescriptionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;

    @Override
    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        log.info("Creating prescription for patient ID: {}", request.getPatientId());

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Hasta bulunamadı: " + request.getPatientId()));

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setPrescriptionNumber(request.getPrescriptionNumber());
        prescription.setIssueDate(request.getIssueDate());
        prescription.setExpiryDate(request.getExpiryDate());
        prescription.setIsActive(true);
        prescription.setDoctorName(request.getDoctorName());
        prescription.setHospitalName(request.getHospitalName());
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setNotes(request.getNotes());

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        // Add medications if provided
        if (request.getMedications() != null && !request.getMedications().isEmpty()) {
            for (PrescriptionMedicationRequest medReq : request.getMedications()) {
                Medication medication = medicationRepository.findById(medReq.getMedicationId())
                        .orElseThrow(() -> new ResourceNotFoundException("İlaç bulunamadı: " + medReq.getMedicationId()));

                PrescriptionMedication pm = new PrescriptionMedication();
                pm.setPrescription(savedPrescription);
                pm.setMedication(medication);
                pm.setDosage(medReq.getDosage());
                pm.setFrequency(medReq.getFrequency());
                pm.setDurationDays(medReq.getDurationDays());
                pm.setStartDate(medReq.getStartDate());
                pm.setEndDate(medReq.getEndDate());
                pm.setInstructions(medReq.getInstructions());
                pm.setQuantityPerDay(medReq.getQuantityPerDay());

                savedPrescription.addMedication(pm);
            }
        }

        log.info("Prescription created with ID: {}", savedPrescription.getId());
        return mapToResponse(savedPrescription);
    }

    @Override
    public PrescriptionResponse updatePrescription(Long id, PrescriptionRequest request) {
        log.info("Updating prescription ID: {}", id);

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reçete bulunamadı: " + id));

        prescription.setPrescriptionNumber(request.getPrescriptionNumber());
        prescription.setIssueDate(request.getIssueDate());
        prescription.setExpiryDate(request.getExpiryDate());
        prescription.setDoctorName(request.getDoctorName());
        prescription.setHospitalName(request.getHospitalName());
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setNotes(request.getNotes());

        Prescription updated = prescriptionRepository.save(prescription);
        log.info("Prescription updated with ID: {}", updated.getId());
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findByIdWithMedications(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reçete bulunamadı: " + id));
        return mapToResponse(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPatientPrescriptions(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getExpiringPrescriptions(int days) {
        LocalDate now = LocalDate.now();
        LocalDate targetDate = now.plusDays(days);
        return prescriptionRepository.findExpiringWithinDays(now, targetDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivatePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reçete bulunamadı: " + id));
        prescription.setIsActive(false);
        prescriptionRepository.save(prescription);
        log.info("Prescription deactivated with ID: {}", id);
    }

    private PrescriptionResponse mapToResponse(Prescription prescription) {
        PrescriptionResponse response = new PrescriptionResponse();
        response.setId(prescription.getId());
        response.setPatientId(prescription.getPatient().getId());
        response.setPatientName(prescription.getPatient().getFullName());
        response.setPrescriptionNumber(prescription.getPrescriptionNumber());
        response.setIssueDate(prescription.getIssueDate());
        response.setExpiryDate(prescription.getExpiryDate());
        response.setIsActive(prescription.getIsActive());
        response.setDoctorName(prescription.getDoctorName());
        response.setHospitalName(prescription.getHospitalName());
        response.setDiagnosis(prescription.getDiagnosis());
        response.setNotes(prescription.getNotes());
        response.setExpired(prescription.isExpired());

        List<PrescriptionMedicationResponse> medications = prescription.getPrescriptionMedications().stream()
                .map(this::mapMedicationToResponse)
                .collect(Collectors.toList());
        response.setMedications(medications);

        response.setCreatedAt(prescription.getCreatedAt());
        response.setUpdatedAt(prescription.getUpdatedAt());
        return response;
    }

    private PrescriptionMedicationResponse mapMedicationToResponse(PrescriptionMedication pm) {
        PrescriptionMedicationResponse response = new PrescriptionMedicationResponse();
        response.setId(pm.getId());
        response.setMedicationId(pm.getMedication().getId());
        response.setMedicationName(pm.getMedication().getName());
        response.setDosage(pm.getDosage());
        response.setFrequency(pm.getFrequency());
        response.setDurationDays(pm.getDurationDays());
        response.setStartDate(pm.getStartDate());
        response.setEndDate(pm.getEndDate());
        response.setInstructions(pm.getInstructions());
        response.setQuantityPerDay(pm.getQuantityPerDay());
        response.setActive(pm.isActive());
        return response;
    }
}