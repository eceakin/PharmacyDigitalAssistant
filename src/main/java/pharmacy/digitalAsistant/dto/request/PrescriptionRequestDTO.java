package pharmacy.digitalAsistant.dto.request;


import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionRequestDTO {
    private Long patientId;
    private String prescriberName;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    // list of medicines inside prescription
    private List<PrescriptionMedicationCreateDTO> medications;
}
