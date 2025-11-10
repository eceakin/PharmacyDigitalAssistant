// PatientRequest.java
package pharmacy.digitalAsistant.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatientRequest {

    @NotBlank(message = "TC Kimlik No boş olamaz")
    @Pattern(regexp = "^[0-9]{11}$", message = "TC Kimlik No 11 haneli olmalıdır")
    private String tcKimlikNo;

    @NotBlank(message = "İsim boş olamaz")
    @Size(min = 2, max = 50, message = "İsim 2-50 karakter arası olmalıdır")
    private String firstName;

    @NotBlank(message = "Soyisim boş olamaz")
    @Size(min = 2, max = 50, message = "Soyisim 2-50 karakter arası olmalıdır")
    private String lastName;

    @Pattern(regexp = "^(05)[0-9]{9}$", message = "Geçerli bir telefon numarası giriniz (05XXXXXXXXX)")
    private String phone;

    @Email(message = "Geçerli bir e-posta adresi giriniz")
    private String email;

    private String chronicDiseases;

    private String address;

    private String notes;
}

