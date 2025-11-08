package pharmacy.digitalAsistant.service.abstracts;

import java.util.List;

import pharmacy.digitalAsistant.dto.request.PatientRequestDTO;
import pharmacy.digitalAsistant.dto.response.PatientResponseDTO;

public interface PatientService {
    PatientResponseDTO register(PatientRequestDTO dto);
    PatientResponseDTO getById(Long id);
    List<PatientResponseDTO> search(String query);
    List<PatientResponseDTO> getAll();
    void delete(Long id);
}
