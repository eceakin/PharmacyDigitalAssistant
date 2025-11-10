package pharmacy.digitalAsistant.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ReportResponse {
    private String reportType;
    private LocalDateTime generatedAt;
    private Map<String, Object> data;
}