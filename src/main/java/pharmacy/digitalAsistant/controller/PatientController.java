package pharmacy.digitalAsistant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.digitalAsistant.dto.request.PatientRequest;
import pharmacy.digitalAsistant.dto.response.PatientResponse;
import pharmacy.digitalAsistant.service.abstracts.PatientService;
import pharmacy.digitalAsistant.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    /**
     * Create a new patient
     * POST /api/patients
     */
    @PostMapping
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientRequest request) {
        PatientResponse response = patientService.createPatient(request);
        return ResponseUtil.created(response, "Hasta başarıyla oluşturuldu");
    }

    /**
     * Update an existing patient
     * PUT /api/patients/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request) {
        PatientResponse response = patientService.updatePatient(id, request);
        return ResponseUtil.success(response, "Hasta başarıyla güncellendi");
    }

    /**
     * Get patient by ID
     * GET /api/patients/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Long id) {
        PatientResponse response = patientService.getPatientById(id);
        return ResponseUtil.success(response);
    }

    /**
     * Get all patients
     * GET /api/patients
     */
    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        List<PatientResponse> responses = patientService.getAllPatients();
        return ResponseUtil.success(responses);
    }

    /**
     * Delete patient by ID
     * DELETE /api/patients/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseUtil.success("Hasta başarıyla silindi");
    }

    /**
     * Search patients by keyword
     * GET /api/patients/search?q=keyword
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchPatients(@RequestParam String q) {
        List<PatientResponse> responses = patientService.searchPatients(q);
        return ResponseUtil.success(responses);
    }

    /**
     * Get patients with chronic diseases
     * GET /api/patients/chronic-diseases
     */
    @GetMapping("/chronic-diseases")
    public ResponseEntity<?> getPatientsWithChronicDiseases() {
        List<PatientResponse> responses = patientService.getPatientsWithChronicDiseases();
        return ResponseUtil.success(responses);
    }

    /**
     * Get patients with active prescriptions
     * GET /api/patients/active-prescriptions
     */
    @GetMapping("/active-prescriptions")
    public ResponseEntity<?> getPatientsWithActivePrescriptions() {
        List<PatientResponse> responses = patientService.getPatientsWithActivePrescriptions();
        return ResponseUtil.success(responses);
    }

    /**
     * Check if TC Kimlik exists
     * GET /api/patients/check-tc?tcKimlik=12345678901
     */
    @GetMapping("/check-tc")
    public ResponseEntity<?> checkTcKimlik(@RequestParam String tcKimlik) {
        boolean exists = patientService.existsByTcKimlik(tcKimlik);
        return ResponseUtil.success(exists);
    }
}