package pharmacy.digitalAsistant.service.abstracts;

import pharmacy.digitalAsistant.dto.request.PatientRequest;
import pharmacy.digitalAsistant.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {

    /**
     * Create a new patient
     */
    PatientResponse createPatient(PatientRequest request);

    /**
     * Update an existing patient
     */
    PatientResponse updatePatient(Long id, PatientRequest request);

    /**
     * Get patient by ID
     */
    PatientResponse getPatientById(Long id);

    /**
     * Get all patients
     */
    List<PatientResponse> getAllPatients();

    /**
     * Delete patient by ID
     */
    void deletePatient(Long id);

    /**
     * Search patients by keyword (name or TC Kimlik)
     */
    List<PatientResponse> searchPatients(String keyword);

    /**
     * Get patients with chronic diseases
     */
    List<PatientResponse> getPatientsWithChronicDiseases();

    /**
     * Get patients with active prescriptions
     */
    List<PatientResponse> getPatientsWithActivePrescriptions();

    /**
     * Check if TC Kimlik already exists
     */
    boolean existsByTcKimlik(String tcKimlikNo);
}