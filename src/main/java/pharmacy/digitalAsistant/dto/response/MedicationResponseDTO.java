package pharmacy.digitalAsistant.dto.response;


import lombok.Data;

@Data
public class MedicationResponseDTO {
    private Long id;
    private String name;
    private String genericName;
    private String manufacturer;
    private String form;
    private String strength;
    private String description;
}