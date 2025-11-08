package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.dto.request.UserRequestDTO;
import pharmacy.digitalAsistant.dto.response.UserResponseDTO;
import pharmacy.digitalAsistant.service.abstracts.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
