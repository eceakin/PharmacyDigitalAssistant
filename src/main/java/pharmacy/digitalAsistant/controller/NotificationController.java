package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.digitalAsistant.dto.response.NotificationResponse;
import pharmacy.digitalAsistant.service.abstracts.NotificationService;
import pharmacy.digitalAsistant.util.ResponseUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getAllNotifications() {
        List<NotificationResponse> responses = notificationService.getAllNotifications();
        return ResponseUtil.success(responses);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getPatientNotifications(@PathVariable Long patientId) {
        List<NotificationResponse> responses = notificationService.getPatientNotifications(patientId);
        return ResponseUtil.success(responses);
    }

    @GetMapping("/failed")
    public ResponseEntity<?> getFailedNotifications() {
        List<NotificationResponse> responses = notificationService.getFailedNotifications();
        return ResponseUtil.success(responses);
    }

    @PostMapping("/send-medication-reminders")
    public ResponseEntity<?> sendMedicationReminders() {
        notificationService.sendMedicationReminders();
        return ResponseUtil.success("İlaç hatırlatmaları gönderildi");
    }

    @PostMapping("/send-prescription-warnings")
    public ResponseEntity<?> sendPrescriptionWarnings() {
        notificationService.sendPrescriptionExpiryWarnings();
        return ResponseUtil.success("Reçete uyarıları gönderildi");
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getNotificationStats() {
        Map<String, Long> stats = Map.of(
                "sent", notificationService.countSentNotifications(),
                "failed", notificationService.countFailedNotifications()
        );
        return ResponseUtil.success(stats);
    }
}