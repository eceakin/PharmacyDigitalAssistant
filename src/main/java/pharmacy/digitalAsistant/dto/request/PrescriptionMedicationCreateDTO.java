package pharmacy.digitalAsistant.dto.request;


import lombok.Data;

@Data
public class PrescriptionMedicationCreateDTO {
    private Long medicationId;
    private Integer quantity;
    private String dosageInstructions;
    private String notes;
}
