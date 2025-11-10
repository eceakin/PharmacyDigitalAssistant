package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacy.digitalAsistant.domain.entity.Notification;
import pharmacy.digitalAsistant.domain.entity.Patient;
import pharmacy.digitalAsistant.domain.entity.Prescription;
import pharmacy.digitalAsistant.domain.entity.PrescriptionMedication;
import pharmacy.digitalAsistant.domain.enums.DeliveryMethod;
import pharmacy.digitalAsistant.domain.enums.NotificationStatus;
import pharmacy.digitalAsistant.domain.enums.NotificationType;
import pharmacy.digitalAsistant.dto.response.NotificationResponse;
import pharmacy.digitalAsistant.repository.NotificationRepository;
import pharmacy.digitalAsistant.repository.PrescriptionMedicationRepository;
import pharmacy.digitalAsistant.repository.PrescriptionRepository;
import pharmacy.digitalAsistant.service.abstracts.NotificationService;
import pharmacy.digitalAsistant.util.NotificationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final PrescriptionMedicationRepository prescriptionMedicationRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getPatientNotifications(Long patientId) {
        return notificationRepository.findByPatientId(patientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getFailedNotifications() {
        return notificationRepository.findByStatus(NotificationStatus.FAILED).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void sendMedicationReminders() {
        log.info("Checking for medication reminders to send");
        
        LocalDate today = LocalDate.now();
        LocalDate threeDaysFromNow = today.plusDays(3);
        
        List<PrescriptionMedication> endingSoon = prescriptionMedicationRepository
                .findEndingBetween(today, threeDaysFromNow);
        
        for (PrescriptionMedication pm : endingSoon) {
            Patient patient = pm.getPrescription().getPatient();
            
            // Check if notification already sent
            boolean alreadySent = notificationRepository.findByPatientId(patient.getId()).stream()
                    .anyMatch(n -> n.getType() == NotificationType.MEDICATION_REMINDER 
                            && n.getCreatedAt().toLocalDate().equals(today));
            
            if (!alreadySent) {
                String message = NotificationUtil.generateMedicationReminderMessage(
                        patient.getFullName(),
                        pm.getMedication().getName(),
                        (int) pm.getRemainingDays()
                );
                
                Notification notification = createNotification(
                        patient,
                        NotificationType.MEDICATION_REMINDER,
                        message
                );
                
                // Simulate sending (in real app, integrate with SMS/Email service)
                boolean sent = simulateSendNotification(notification);
                
                if (sent) {
                    notification.markAsSent();
                    log.info("Medication reminder sent to patient: {}", patient.getFullName());
                } else {
                    notification.markAsFailed("Gönderim başarısız");
                    log.error("Failed to send medication reminder to patient: {}", patient.getFullName());
                }
                
                notificationRepository.save(notification);
            }
        }
    }

    @Override
    public void sendPrescriptionExpiryWarnings() {
        log.info("Checking for prescription expiry warnings to send");
        
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);
        
        List<Prescription> expiringPrescriptions = prescriptionRepository
                .findExpiringWithinDays(today, sevenDaysFromNow);
        
        for (Prescription prescription : expiringPrescriptions) {
            Patient patient = prescription.getPatient();
            
            // Check if notification already sent
            boolean alreadySent = notificationRepository.findByPatientId(patient.getId()).stream()
                    .anyMatch(n -> n.getType() == NotificationType.PRESCRIPTION_EXPIRY 
                            && n.getCreatedAt().toLocalDate().equals(today));
            
            if (!alreadySent) {
                int daysRemaining = (int) java.time.temporal.ChronoUnit.DAYS.between(today, prescription.getExpiryDate());
                
                String message = NotificationUtil.generatePrescriptionExpiryMessage(
                        patient.getFullName(),
                        prescription.getExpiryDate(),
                        daysRemaining
                );
                
                Notification notification = createNotification(
                        patient,
                        NotificationType.PRESCRIPTION_EXPIRY,
                        message
                );
                
                boolean sent = simulateSendNotification(notification);
                
                if (sent) {
                    notification.markAsSent();
                    log.info("Prescription expiry warning sent to patient: {}", patient.getFullName());
                } else {
                    notification.markAsFailed("Gönderim başarısız");
                    log.error("Failed to send prescription expiry warning to patient: {}", patient.getFullName());
                }
                
                notificationRepository.save(notification);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long countSentNotifications() {
        return notificationRepository.countByStatus(NotificationStatus.SENT);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFailedNotifications() {
        return notificationRepository.countByStatus(NotificationStatus.FAILED);
    }

    private Notification createNotification(Patient patient, NotificationType type, String message) {
        Notification notification = new Notification();
        notification.setPatient(patient);
        notification.setType(type);
        notification.setMessage(message);
        notification.setStatus(NotificationStatus.PENDING);
        
        // Determine delivery method based on patient contact info
        if (NotificationUtil.canSendSms(patient.getPhone()) && NotificationUtil.canSendEmail(patient.getEmail())) {
            notification.setDeliveryMethod(DeliveryMethod.BOTH);
        } else if (NotificationUtil.canSendSms(patient.getPhone())) {
            notification.setDeliveryMethod(DeliveryMethod.SMS);
        } else if (NotificationUtil.canSendEmail(patient.getEmail())) {
            notification.setDeliveryMethod(DeliveryMethod.EMAIL);
        } else {
            notification.setDeliveryMethod(DeliveryMethod.SMS);
        }
        
        return notification;
    }

    private boolean simulateSendNotification(Notification notification) {
        // In real application, integrate with SMS/Email service providers
        // For now, simulate successful sending
        return true; // 90% success rate simulation
    }

    private NotificationResponse mapToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setPatientId(notification.getPatient().getId());
        response.setPatientName(notification.getPatient().getFullName());
        response.setType(notification.getType());
        response.setMessage(notification.getMessage());
        response.setSentAt(notification.getSentAt());
        response.setStatus(notification.getStatus());
        response.setDeliveryMethod(notification.getDeliveryMethod());
        response.setErrorMessage(notification.getErrorMessage());
        response.setCreatedAt(notification.getCreatedAt());
        return response;
    }
}
