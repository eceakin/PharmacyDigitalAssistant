package pharmacy.digitalAsistant.service.impl;
import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.domain.entity.User;
import pharmacy.digitalAsistant.domain.enums.UserRole;
import pharmacy.digitalAsistant.dto.request.UserRequestDTO;
import pharmacy.digitalAsistant.dto.response.UserResponseDTO;
import pharmacy.digitalAsistant.repository.UserRepository;
import pharmacy.digitalAsistant.service.abstracts.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setPassword(encoder.encode(dto.getPassword()));   // security requirement in PDF
        user.setRole(UserRole.valueOf(dto.getRole()));

        User saved = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setFullName(saved.getFullName());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole().name());
        return response;
    }

    @Override
    public UserResponseDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO res = new UserResponseDTO();
        res.setId(user.getId());
        res.setUsername(user.getUsername());
        res.setFullName(user.getFullName());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole().name());
        return res;
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream().map(u -> {
            UserResponseDTO r = new UserResponseDTO();
            r.setId(u.getId());
            r.setUsername(u.getUsername());
            r.setFullName(u.getFullName());
            r.setEmail(u.getEmail());
            r.setRole(u.getRole().name());
            return r;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
