package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.dto.request.InventoryRequestDTO;
import pharmacy.digitalAsistant.dto.response.InventoryResponseDTO;
import pharmacy.digitalAsistant.service.abstracts.InventoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> addStock(@Valid @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(inventoryService.addStock(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.get(id));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponseDTO>> lowStock() {
        return ResponseEntity.ok(inventoryService.getLowStock());
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<InventoryResponseDTO>> expiring(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return ResponseEntity.ok(inventoryService.getExpiringBetween(from, to));
    }
}
