package pharmacy.digitalAsistant.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PatientResponse {

    private Long id;
    private String tcKimlikNo;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String email;
    private String chronicDiseases;
    private String address;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}