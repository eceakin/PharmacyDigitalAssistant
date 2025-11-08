package pharmacy.digitalAsistant.dto.response;


import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionResponseDTO {
    private Long id;
    private String prescriberName;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String notes;

    private List<PrescriptionMedicationResponseDTO> medications;
}
