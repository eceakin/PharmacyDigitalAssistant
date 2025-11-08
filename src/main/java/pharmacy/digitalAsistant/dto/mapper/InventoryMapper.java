package pharmacy.digitalAsistant.dto.mapper;
import org.mapstruct.Mapper;

import pharmacy.digitalAsistant.domain.entity.Inventory;
import pharmacy.digitalAsistant.dto.request.InventoryRequestDTO;
import pharmacy.digitalAsistant.dto.response.InventoryResponseDTO;

@Mapper(componentModel = "spring", uses = {MedicationMapper.class})
public interface InventoryMapper {

    Inventory toEntity(InventoryRequestDTO dto);

    InventoryResponseDTO toDTO(Inventory entity);
}
