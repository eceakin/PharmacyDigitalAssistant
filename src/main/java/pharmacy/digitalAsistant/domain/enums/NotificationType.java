package pharmacy.digitalAsistant.domain.enums;


public enum NotificationType {
    MEDICATION_REMINDER,      // İlaç bitim hatırlatması
    PRESCRIPTION_EXPIRY,      // Reçete bitiş uyarısı
    LOW_STOCK_ALERT,          // Düşük stok uyarısı (internal)
    EXPIRY_DATE_WARNING,      // SKT uyarısı (internal)
    WELCOME_MESSAGE           // Hoş geldiniz mesajı
}