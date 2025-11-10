package pharmacy.digitalAsistant.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrescriptionResponse {
    private Long id;
    private Long patientId;
    private String patientName;
    private String prescriptionNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private Boolean isActive;
    private String doctorName;
    private String hospitalName;
    private String diagnosis;
    private String notes;
    private boolean expired;
    private List<PrescriptionMedicationResponse> medications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}