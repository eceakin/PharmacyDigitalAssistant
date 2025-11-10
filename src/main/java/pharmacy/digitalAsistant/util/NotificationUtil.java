package pharmacy.digitalAsistant.util;

import pharmacy.digitalAsistant.domain.enums.*;

import java.time.LocalDate;

public class NotificationUtil {

    /**
     * Generate medication reminder message
     */
    public static String generateMedicationReminderMessage(
            String patientName,
            String medicationName,
            int daysRemaining
    ) {
        return String.format(
                "Sayın %s, %s ilacınız %d gün içinde bitecektir. " +
                "Lütfen eczanemizden yeni reçetenizi alınız.",
                patientName,
                medicationName,
                daysRemaining
        );
    }

    /**
     * Generate prescription expiry message
     */
    public static String generatePrescriptionExpiryMessage(
            String patientName,
            LocalDate expiryDate,
            int daysRemaining
    ) {
        return String.format(
                "Sayın %s, reçeteniz %s tarihinde (%d gün sonra) geçerliliğini yitirecektir. " +
                "Yeni reçete almanız gerekmektedir.",
                patientName,
                DateUtil.formatDate(expiryDate),
                daysRemaining
        );
    }

    /**
     * Generate low stock alert message for staff
     */
    public static String generateLowStockAlertMessage(
            String medicationName,
            int currentStock,
            int minimumStock
    ) {
        return String.format(
                "UYARI: %s ilacının stok seviyesi kritik seviyenin altına düştü! " +
                "Mevcut: %d adet, Minimum: %d adet",
                medicationName,
                currentStock,
                minimumStock
        );
    }

    /**
     * Generate expiry date warning message for staff
     */
    public static String generateExpiryWarningMessage(
            String medicationName,
            LocalDate expiryDate,
            int monthsRemaining
    ) {
        return String.format(
                "UYARI: %s ilacının son kullanma tarihi yaklaşıyor! " +
                "SKT: %s (%d ay kaldı)",
                medicationName,
                DateUtil.formatDate(expiryDate),
                monthsRemaining
        );
    }

    /**
     * Generate welcome message for new patient
     */
    public static String generateWelcomeMessage(String patientName, String pharmacyName) {
        return String.format(
                "Sayın %s, %s eczanesine hoş geldiniz! " +
                "İlaç hatırlatma sistemimize kaydınız başarıyla tamamlanmıştır.",
                patientName,
                pharmacyName
        );
    }

    /**
     * Get notification subject based on type
     */
    public static String getNotificationSubject(NotificationType type) {
        return switch (type) {
            case MEDICATION_REMINDER -> "İlaç Hatırlatması";
            case PRESCRIPTION_EXPIRY -> "Reçete Yenileme Gerekli";
            case LOW_STOCK_ALERT -> "Düşük Stok Uyarısı";
            case EXPIRY_DATE_WARNING -> "Son Kullanma Tarihi Uyarısı";
            case WELCOME_MESSAGE -> "Hoş Geldiniz";
            default -> "Bildirim";
        };
    }

    /**
     * Format phone number for SMS (remove formatting, keep only digits)
     */
    public static String formatPhoneForSms(String phone) {
        if (phone == null) {
            return null;
        }
        // Remove all non-digit characters
        String cleaned = phone.replaceAll("[^0-9]", "");
        
        // Ensure it starts with country code (90 for Turkey)
        if (cleaned.startsWith("0")) {
            cleaned = "90" + cleaned.substring(1);
        } else if (!cleaned.startsWith("90")) {
            cleaned = "90" + cleaned;
        }
        
        return cleaned;
    }

    /**
     * Validate email for notification
     */
    public static boolean canSendEmail(String email) {
        return email != null && !email.isEmpty() && ValidationUtil.isValidEmail(email);
    }

    /**
     * Validate phone for SMS notification
     */
    public static boolean canSendSms(String phone) {
        return phone != null && !phone.isEmpty() && ValidationUtil.isValidPhoneNumber(phone);
    }

    /**
     * Truncate message for SMS (max 160 characters for standard SMS)
     */
    public static String truncateForSms(String message, int maxLength) {
        if (message == null) {
            return "";
        }
        if (message.length() <= maxLength) {
            return message;
        }
        return message.substring(0, maxLength - 3) + "...";
    }

    /**
     * Create HTML email body
     */
    public static String createHtmlEmailBody(String title, String message) {
        return String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); 
                                  color: white; padding: 20px; border-radius: 10px 10px 0 0; }
                        .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                        .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }
                        .button { background: #667eea; color: white; padding: 12px 30px; 
                                  text-decoration: none; border-radius: 5px; display: inline-block; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h2>%s</h2>
                        </div>
                        <div class="content">
                            <p>%s</p>
                        </div>
                        <div class="footer">
                            <p>Bu e-posta Ecz-Asistan sistemi tarafından otomatik olarak gönderilmiştir.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, title, message);
    }

    /**
     * Calculate notification priority based on days remaining
     */
    public static String calculatePriority(int daysRemaining) {
        if (daysRemaining <= 1) {
            return "URGENT";
        } else if (daysRemaining <= 3) {
            return "HIGH";
        } else if (daysRemaining <= 7) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    /**
     * Check if notification should be sent based on time
     */
    public static boolean shouldSendNotificationNow(int targetHour) {
        int currentHour = java.time.LocalTime.now().getHour();
        // Allow sending within 1 hour of target time
        return Math.abs(currentHour - targetHour) <= 1;
    }
}