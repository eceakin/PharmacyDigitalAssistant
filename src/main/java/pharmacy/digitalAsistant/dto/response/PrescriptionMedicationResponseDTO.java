package pharmacy.digitalAsistant.dto.response;


import lombok.Data;

@Data
public class PrescriptionMedicationResponseDTO {
    private Long id;
    private MedicationResponseDTO medication;
    private Integer quantity;
    private String dosageInstructions;
    private String notes;
}
