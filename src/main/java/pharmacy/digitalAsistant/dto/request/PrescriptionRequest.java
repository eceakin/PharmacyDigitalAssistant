package pharmacy.digitalAsistant.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionRequest {
    @NotNull(message = "Hasta ID boş olamaz")
    private Long patientId;

    private String prescriptionNumber;

    @NotNull(message = "Düzenleme tarihi boş olamaz")
    private LocalDate issueDate;

    @NotNull(message = "Geçerlilik tarihi boş olamaz")
    private LocalDate expiryDate;

    private String doctorName;
    private String hospitalName;
    private String diagnosis;
    private String notes;

    private List<PrescriptionMedicationRequest> medications;
}