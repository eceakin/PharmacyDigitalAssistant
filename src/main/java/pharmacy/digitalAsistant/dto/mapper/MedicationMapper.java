package pharmacy.digitalAsistant.dto.mapper;


import org.mapstruct.Mapper;

import pharmacy.digitalAsistant.domain.entity.Medication;
import pharmacy.digitalAsistant.dto.request.MedicationRequestDTO;
import pharmacy.digitalAsistant.dto.response.MedicationResponseDTO;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    Medication toEntity(MedicationRequestDTO dto);

    MedicationResponseDTO toDTO(Medication entity);
}
