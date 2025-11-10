
// PrescriptionController.java
package pharmacy.digitalAsistant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.digitalAsistant.dto.request.PrescriptionRequest;
import pharmacy.digitalAsistant.dto.response.PrescriptionResponse;
import pharmacy.digitalAsistant.service.abstracts.PrescriptionService;
import pharmacy.digitalAsistant.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<?> createPrescription(@Valid @RequestBody PrescriptionRequest request) {
        PrescriptionResponse response = prescriptionService.createPrescription(request);
        return ResponseUtil.created(response, "Reçete başarıyla oluşturuldu");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrescription(@PathVariable Long id, @Valid @RequestBody PrescriptionRequest request) {
        PrescriptionResponse response = prescriptionService.updatePrescription(id, request);
        return ResponseUtil.success(response, "Reçete başarıyla güncellendi");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrescriptionById(@PathVariable Long id) {
        PrescriptionResponse response = prescriptionService.getPrescriptionById(id);
        return ResponseUtil.success(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllPrescriptions() {
        List<PrescriptionResponse> responses = prescriptionService.getAllPrescriptions();
        return ResponseUtil.success(responses);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getPatientPrescriptions(@PathVariable Long patientId) {
        List<PrescriptionResponse> responses = prescriptionService.getPatientPrescriptions(patientId);
        return ResponseUtil.success(responses);
    }

    @GetMapping("/expiring")
    public ResponseEntity<?> getExpiringPrescriptions(@RequestParam(defaultValue = "7") int days) {
        List<PrescriptionResponse> responses = prescriptionService.getExpiringPrescriptions(days);
        return ResponseUtil.success(responses);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivatePrescription(@PathVariable Long id) {
        prescriptionService.deactivatePrescription(id);
        return ResponseUtil.success("Reçete pasif hale getirildi");
    }
}