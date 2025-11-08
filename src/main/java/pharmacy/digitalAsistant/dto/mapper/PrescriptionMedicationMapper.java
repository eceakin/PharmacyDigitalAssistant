package pharmacy.digitalAsistant.dto.mapper;

import org.mapstruct.Mapper;

import pharmacy.digitalAsistant.domain.entity.PrescriptionMedication;
import pharmacy.digitalAsistant.dto.request.PrescriptionMedicationCreateDTO;
import pharmacy.digitalAsistant.dto.response.PrescriptionMedicationResponseDTO;

@Mapper(componentModel = "spring", uses = {MedicationMapper.class})
public interface PrescriptionMedicationMapper {

    PrescriptionMedication toEntity(PrescriptionMedicationCreateDTO dto);

    PrescriptionMedicationResponseDTO toDTO(PrescriptionMedication entity);
}
