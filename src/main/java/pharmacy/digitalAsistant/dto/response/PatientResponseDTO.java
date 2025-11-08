package pharmacy.digitalAsistant.dto.response;


import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PatientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalId;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String address;
    private String chronicConditions;
    private String allergies;

    private List<PrescriptionResponseDTO> prescriptions;
}
