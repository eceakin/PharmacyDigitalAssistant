package pharmacy.digitalAsistant.dto.response;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationResponseDTO {
    private Long id;
    private String title;
    private String message;
    private String type;
    private String status;
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
}
