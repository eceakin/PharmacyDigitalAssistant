package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.dto.request.NotificationRequestDTO;
import pharmacy.digitalAsistant.dto.response.NotificationResponseDTO;
import pharmacy.digitalAsistant.service.abstracts.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@Valid @RequestBody NotificationRequestDTO dto) {
        return ResponseEntity.ok(notificationService.create(dto));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<NotificationResponseDTO>> pending() {
        return ResponseEntity.ok(notificationService.getPendingToSend());
    }

    @PutMapping("/{id}/sent")
    public ResponseEntity<Void> markSent(@PathVariable Long id) {
        notificationService.markAsSent(id);
        return ResponseEntity.noContent().build();
    }
}
