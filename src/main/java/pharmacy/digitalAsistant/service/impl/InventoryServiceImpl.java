package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.domain.entity.Inventory;
import pharmacy.digitalAsistant.domain.entity.Medication;
import pharmacy.digitalAsistant.dto.mapper.InventoryMapper;
import pharmacy.digitalAsistant.dto.request.InventoryRequestDTO;
import pharmacy.digitalAsistant.dto.response.InventoryResponseDTO;
import pharmacy.digitalAsistant.repository.InventoryRepository;
import pharmacy.digitalAsistant.repository.MedicationRepository;
import pharmacy.digitalAsistant.service.abstracts.InventoryService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MedicationRepository medicationRepository;
    private final InventoryMapper mapper;

    @Override
    public InventoryResponseDTO addStock(InventoryRequestDTO dto) {
        Medication med = medicationRepository.findById(dto.getMedicationId())
                .orElseThrow(() -> new RuntimeException("Medication not found"));

        Inventory entity = mapper.toEntity(dto);
        entity.setMedication(med);

        Inventory saved = inventoryRepository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public InventoryResponseDTO get(Long id) {
        return mapper.toDTO(inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found")));
    }

    @Override
    public List<InventoryResponseDTO> getLowStock() {
        return inventoryRepository.findLowStockItems().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> getExpiringBetween(LocalDate from, LocalDate to) {
        return inventoryRepository.findItemsExpiringBetween(from, to).stream()
                .map(mapper::toDTO)
                .toList();
    }
}
