package pharmacy.digitalAsistant.service.abstracts;

import java.util.List;

import pharmacy.digitalAsistant.dto.request.UserRequestDTO;
import pharmacy.digitalAsistant.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO getUser(Long id);
    List<UserResponseDTO> getAll();
    void deleteUser(Long id);
}
