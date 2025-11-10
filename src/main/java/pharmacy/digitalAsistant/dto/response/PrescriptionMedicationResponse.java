package pharmacy.digitalAsistant.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PrescriptionMedicationResponse {
    private Long id;
    private Long medicationId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private Integer durationDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String instructions;
    private Integer quantityPerDay;
    private boolean active;
}