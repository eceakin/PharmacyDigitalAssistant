package pharmacy.digitalAsistant.service.abstracts;

import java.util.List;

import pharmacy.digitalAsistant.dto.request.PrescriptionRequestDTO;
import pharmacy.digitalAsistant.dto.response.PrescriptionResponseDTO;

public interface PrescriptionService {
    PrescriptionResponseDTO create(PrescriptionRequestDTO dto);
    PrescriptionResponseDTO get(Long id);
    List<PrescriptionResponseDTO> getByPatient(Long patientId);
}
