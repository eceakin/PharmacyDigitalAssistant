package pharmacy.digitalAsistant.service.abstracts;

import java.util.List;

import pharmacy.digitalAsistant.dto.request.NotificationRequestDTO;
import pharmacy.digitalAsistant.dto.response.NotificationResponseDTO;

public interface NotificationService {
    NotificationResponseDTO create(NotificationRequestDTO dto);
    List<NotificationResponseDTO> getPendingToSend();
    void markAsSent(Long id);
}
