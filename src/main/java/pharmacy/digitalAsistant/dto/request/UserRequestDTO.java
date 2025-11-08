package pharmacy.digitalAsistant.dto.request;


import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role; // string -> enum
}
