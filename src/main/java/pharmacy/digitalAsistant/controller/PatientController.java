package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.dto.request.PatientRequestDTO;
import pharmacy.digitalAsistant.dto.response.PatientResponseDTO;
import pharmacy.digitalAsistant.service.abstracts.PatientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> register(@Valid @RequestBody PatientRequestDTO dto) {
        return ResponseEntity.ok(patientService.register(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAll() {
        return ResponseEntity.ok(patientService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientResponseDTO>> search(@RequestParam String q) {
        return ResponseEntity.ok(patientService.search(q));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
