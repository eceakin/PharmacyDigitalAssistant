package pharmacy.digitalAsistant.service.abstracts;

import java.util.List;

import pharmacy.digitalAsistant.dto.request.MedicationRequestDTO;
import pharmacy.digitalAsistant.dto.response.MedicationResponseDTO;

public interface MedicationService {
    MedicationResponseDTO create(MedicationRequestDTO dto);
    MedicationResponseDTO getById(Long id);
    List<MedicationResponseDTO> search(String name);
    List<MedicationResponseDTO> getAll();
}
