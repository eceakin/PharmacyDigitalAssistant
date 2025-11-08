package pharmacy.digitalAsistant.dto.request;


import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRequestDTO {
    private String firstName;
    private String lastName;
    private String nationalId;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String address;
    private String chronicConditions;
    private String allergies;
}
