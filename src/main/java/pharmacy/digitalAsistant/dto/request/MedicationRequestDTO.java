package pharmacy.digitalAsistant.dto.request;


import lombok.Data;

@Data
public class MedicationRequestDTO {
    private String name;
    private String genericName;
    private String manufacturer;
    private String form;
    private String strength;
    private String description;
}
