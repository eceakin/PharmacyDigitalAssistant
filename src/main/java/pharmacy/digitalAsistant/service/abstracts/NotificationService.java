package pharmacy.digitalAsistant.service.abstracts;


import pharmacy.digitalAsistant.dto.response.NotificationResponse;
import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getAllNotifications();
    List<NotificationResponse> getPatientNotifications(Long patientId);
    List<NotificationResponse> getFailedNotifications();
    void sendMedicationReminders();
    void sendPrescriptionExpiryWarnings();
    long countSentNotifications();
    long countFailedNotifications();
}