package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.dto.request.PrescriptionRequestDTO;
import pharmacy.digitalAsistant.dto.response.PrescriptionResponseDTO;
import pharmacy.digitalAsistant.service.abstracts.PrescriptionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prescriptions")
@CrossOrigin
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<PrescriptionResponseDTO> create(@Valid @RequestBody PrescriptionRequestDTO dto) {
        return ResponseEntity.ok(prescriptionService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.get(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponseDTO>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getByPatient(patientId));
    }
}

