package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacy.digitalAsistant.domain.entity.Patient;
import pharmacy.digitalAsistant.dto.request.PatientRequest;
import pharmacy.digitalAsistant.dto.response.PatientResponse;
import pharmacy.digitalAsistant.exception.ResourceNotFoundException;
import pharmacy.digitalAsistant.exception.ValidationException;
import pharmacy.digitalAsistant.repository.PatientRepository;
import pharmacy.digitalAsistant.service.abstracts.PatientService;
import pharmacy.digitalAsistant.util.ValidationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponse createPatient(PatientRequest request) {
        log.info("Creating new patient with TC Kimlik: {}", request.getTcKimlikNo());

        // Validate TC Kimlik
        if (!ValidationUtil.isValidTcKimlik(request.getTcKimlikNo())) {
            throw new ValidationException("Geçersiz TC Kimlik numarası");
        }

        // Check if TC Kimlik already exists
        if (patientRepository.existsByTcKimlikNo(request.getTcKimlikNo())) {
            throw new ValidationException("Bu TC Kimlik numarası zaten kayıtlı");
        }

        // Validate email if provided
        if (request.getEmail() != null && !ValidationUtil.isValidEmail(request.getEmail())) {
            throw new ValidationException("Geçersiz e-posta adresi");
        }

        // Validate phone if provided
        if (request.getPhone() != null && !ValidationUtil.isValidPhoneNumber(request.getPhone())) {
            throw new ValidationException("Geçersiz telefon numarası");
        }

        Patient patient = new Patient();
        patient.setTcKimlikNo(request.getTcKimlikNo());
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setChronicDiseases(request.getChronicDiseases());
        patient.setAddress(request.getAddress());
        patient.setNotes(request.getNotes());

        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient created successfully with ID: {}", savedPatient.getId());

        return mapToResponse(savedPatient);
    }

    @Override
    public PatientResponse updatePatient(Long id, PatientRequest request) {
        log.info("Updating patient with ID: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hasta bulunamadı: " + id));

        // If TC Kimlik is being changed, validate and check uniqueness
        if (!patient.getTcKimlikNo().equals(request.getTcKimlikNo())) {
            if (!ValidationUtil.isValidTcKimlik(request.getTcKimlikNo())) {
                throw new ValidationException("Geçersiz TC Kimlik numarası");
            }
            if (patientRepository.existsByTcKimlikNo(request.getTcKimlikNo())) {
                throw new ValidationException("Bu TC Kimlik numarası zaten kayıtlı");
            }
            patient.setTcKimlikNo(request.getTcKimlikNo());
        }

        // Validate email if provided
        if (request.getEmail() != null && !ValidationUtil.isValidEmail(request.getEmail())) {
            throw new ValidationException("Geçersiz e-posta adresi");
        }

        // Validate phone if provided
        if (request.getPhone() != null && !ValidationUtil.isValidPhoneNumber(request.getPhone())) {
            throw new ValidationException("Geçersiz telefon numarası");
        }

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setChronicDiseases(request.getChronicDiseases());
        patient.setAddress(request.getAddress());
        patient.setNotes(request.getNotes());

        Patient updatedPatient = patientRepository.save(patient);
        log.info("Patient updated successfully with ID: {}", updatedPatient.getId());

        return mapToResponse(updatedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        log.info("Fetching patient with ID: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hasta bulunamadı: " + id));

        return mapToResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        log.info("Fetching all patients");

        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePatient(Long id) {
        log.info("Deleting patient with ID: {}", id);

        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hasta bulunamadı: " + id);
        }

        patientRepository.deleteById(id);
        log.info("Patient deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> searchPatients(String keyword) {
        log.info("Searching patients with keyword: {}", keyword);

        return patientRepository.searchPatients(keyword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getPatientsWithChronicDiseases() {
        log.info("Fetching patients with chronic diseases");

        return patientRepository.findPatientsWithChronicDiseases().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getPatientsWithActivePrescriptions() {
        log.info("Fetching patients with active prescriptions");

        return patientRepository.findPatientsWithActivePrescriptions().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTcKimlik(String tcKimlikNo) {
        return patientRepository.existsByTcKimlikNo(tcKimlikNo);
    }

    // Helper method to map entity to response DTO
    private PatientResponse mapToResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setTcKimlikNo(patient.getTcKimlikNo());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setFullName(patient.getFullName());
        response.setPhone(patient.getPhone());
        response.setEmail(patient.getEmail());
        response.setChronicDiseases(patient.getChronicDiseases());
        response.setAddress(patient.getAddress());
        response.setNotes(patient.getNotes());
        response.setCreatedAt(patient.getCreatedAt());
        response.setUpdatedAt(patient.getUpdatedAt());
        return response;
    }
}