package pharmacy.digitalAsistant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.digitalAsistant.dto.request.MedicationRequest;
import pharmacy.digitalAsistant.dto.response.MedicationResponse;
import pharmacy.digitalAsistant.service.abstracts.MedicationService;
import pharmacy.digitalAsistant.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<?> createMedication(@Valid @RequestBody MedicationRequest request) {
        MedicationResponse response = medicationService.createMedication(request);
        return ResponseUtil.created(response, "İlaç başarıyla oluşturuldu");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedication(@PathVariable Long id, @Valid @RequestBody MedicationRequest request) {
        MedicationResponse response = medicationService.updateMedication(id, request);
        return ResponseUtil.success(response, "İlaç başarıyla güncellendi");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicationById(@PathVariable Long id) {
        MedicationResponse response = medicationService.getMedicationById(id);
        return ResponseUtil.success(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllMedications() {
        List<MedicationResponse> responses = medicationService.getAllMedications();
        return ResponseUtil.success(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseUtil.success("İlaç başarıyla silindi");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMedications(@RequestParam String q) {
        List<MedicationResponse> responses = medicationService.searchMedications(q);
        return ResponseUtil.success(responses);
    }

    @GetMapping("/check-barcode")
    public ResponseEntity<?> checkBarcode(@RequestParam String barcode) {
        boolean exists = medicationService.existsByBarcode(barcode);
        return ResponseUtil.success(exists);
    }
}
