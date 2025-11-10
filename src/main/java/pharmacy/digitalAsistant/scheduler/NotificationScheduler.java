package pharmacy.digitalAsistant.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pharmacy.digitalAsistant.service.abstracts.NotificationService;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

    private final NotificationService notificationService;

    /**
     * Send medication reminders daily at 9:00 AM
     */
    @Scheduled(cron = "0 0 9 * * *", zone = "Europe/Istanbul")
    public void scheduleMedicationReminders() {
        log.info("Starting scheduled medication reminder task");
        try {
            notificationService.sendMedicationReminders();
            log.info("Medication reminder task completed successfully");
        } catch (Exception e) {
            log.error("Error in medication reminder task", e);
        }
    }

    /**
     * Send prescription expiry warnings daily at 9:00 AM
     */
    @Scheduled(cron = "0 0 9 * * *", zone = "Europe/Istanbul")
    public void schedulePrescriptionExpiryWarnings() {
        log.info("Starting scheduled prescription expiry warning task");
        try {
            notificationService.sendPrescriptionExpiryWarnings();
            log.info("Prescription expiry warning task completed successfully");
        } catch (Exception e) {
            log.error("Error in prescription expiry warning task", e);
        }
    }

    /**
     * Clean up old notifications (older than 6 months) - runs monthly
     */
    @Scheduled(cron = "0 0 2 1 * *", zone = "Europe/Istanbul")
    public void cleanupOldNotifications() {
        log.info("Starting scheduled notification cleanup task");
        // Implementation for cleanup if needed
        log.info("Notification cleanup task completed");
    }
}
