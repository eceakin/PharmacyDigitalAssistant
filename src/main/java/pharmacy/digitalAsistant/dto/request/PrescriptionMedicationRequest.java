// PrescriptionMedicationRequest.java
package pharmacy.digitalAsistant.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PrescriptionMedicationRequest {
    @NotNull(message = "İlaç ID boş olamaz")
    private Long medicationId;

    @NotBlank(message = "Doz boş olamaz")
    private String dosage;

    @NotBlank(message = "Kullanım sıklığı boş olamaz")
    private String frequency;

    @NotNull(message = "Süre boş olamaz")
    @Min(value = 1, message = "Süre en az 1 gün olmalıdır")
    private Integer durationDays;

    @NotNull(message = "Başlangıç tarihi boş olamaz")
    private LocalDate startDate;

    @NotNull(message = "Bitiş tarihi boş olamaz")
    private LocalDate endDate;

    private String instructions;
    private Integer quantityPerDay;
}