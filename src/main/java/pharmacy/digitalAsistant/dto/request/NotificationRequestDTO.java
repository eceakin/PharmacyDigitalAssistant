package pharmacy.digitalAsistant.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationRequestDTO {
    private Long patientId;
    private String title;
    private String message;
    private String type;      // enum string
    private LocalDateTime scheduledAt;
}
