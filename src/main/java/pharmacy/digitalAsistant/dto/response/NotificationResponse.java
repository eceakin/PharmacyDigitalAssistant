package pharmacy.digitalAsistant.dto.response;

import lombok.Data;
import pharmacy.digitalAsistant.domain.enums.DeliveryMethod;
import pharmacy.digitalAsistant.domain.enums.NotificationStatus;
import pharmacy.digitalAsistant.domain.enums.NotificationType;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private Long patientId;
    private String patientName;
    private NotificationType type;
    private String message;
    private LocalDateTime sentAt;
    private NotificationStatus status;
    private DeliveryMethod deliveryMethod;
    private String errorMessage;
    private LocalDateTime createdAt;
}