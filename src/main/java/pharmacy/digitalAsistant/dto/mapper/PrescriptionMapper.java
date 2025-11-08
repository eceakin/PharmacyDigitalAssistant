package pharmacy.digitalAsistant.dto.mapper;
import org.mapstruct.Mapper;

import pharmacy.digitalAsistant.domain.entity.Prescription;
import pharmacy.digitalAsistant.dto.request.PrescriptionRequestDTO;
import pharmacy.digitalAsistant.dto.response.PrescriptionResponseDTO;

@Mapper(componentModel = "spring", uses = {PrescriptionMedicationMapper.class})
public interface PrescriptionMapper {

    Prescription toEntity(PrescriptionRequestDTO dto);

    PrescriptionResponseDTO toDTO(Prescription entity);
}
