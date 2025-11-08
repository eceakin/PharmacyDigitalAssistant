package pharmacy.digitalAsistant.service.abstracts;

import java.time.LocalDate;
import java.util.List;

import pharmacy.digitalAsistant.dto.request.InventoryRequestDTO;
import pharmacy.digitalAsistant.dto.response.InventoryResponseDTO;

public interface InventoryService {
    InventoryResponseDTO addStock(InventoryRequestDTO dto);
    InventoryResponseDTO get(Long id);
    List<InventoryResponseDTO> getLowStock();
    List<InventoryResponseDTO> getExpiringBetween(LocalDate from, LocalDate to);
}
