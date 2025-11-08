package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.dto.request.MedicationRequestDTO;
import pharmacy.digitalAsistant.dto.response.MedicationResponseDTO;
import pharmacy.digitalAsistant.service.abstracts.MedicationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medications")
@CrossOrigin
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationResponseDTO> create(@Valid @RequestBody MedicationRequestDTO dto) {
        return ResponseEntity.ok(medicationService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(medicationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MedicationResponseDTO>> getAll() {
        return ResponseEntity.ok(medicationService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicationResponseDTO>> search(@RequestParam String q) {
        return ResponseEntity.ok(medicationService.search(q));
    }
}
