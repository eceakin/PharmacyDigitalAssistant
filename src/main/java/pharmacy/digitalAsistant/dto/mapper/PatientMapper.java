package pharmacy.digitalAsistant.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pharmacy.digitalAsistant.domain.entity.Patient;
import pharmacy.digitalAsistant.dto.request.PatientRequestDTO;
import pharmacy.digitalAsistant.dto.response.PatientResponseDTO;

@Mapper(componentModel = "spring", uses = {PrescriptionMapper.class})
public interface PatientMapper {

    Patient toEntity(PatientRequestDTO dto);

    @Mapping(target = "prescriptions", source = "prescriptions")
    PatientResponseDTO toDTO(Patient patient);
}
