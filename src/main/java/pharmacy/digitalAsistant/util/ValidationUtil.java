package pharmacy.digitalAsistant.util;


import java.util.regex.Pattern;

public class ValidationUtil {

    // Turkish ID number length
    private static final int TC_KIMLIK_LENGTH = 11;
    
    // Email regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Turkish phone number pattern (05XX XXX XX XX)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(05)[0-9]{9}$"
    );
    
    // Barcode pattern (8-13 digits)
    private static final Pattern BARCODE_PATTERN = Pattern.compile(
            "^[0-9]{8,13}$"
    );

    /**
     * Validate Turkish TC Kimlik Number
     * Algorithm:
     * 1. Must be 11 digits
     * 2. First digit cannot be 0
     * 3. Sum of first 10 digits % 10 should equal 11th digit
     * 4. (sum of odd positions * 7 - sum of even positions) % 10 should equal 10th digit
     */
    public static boolean isValidTcKimlik(String tcKimlik) {
        if (tcKimlik == null || tcKimlik.length() != TC_KIMLIK_LENGTH) {
            return false;
        }

        // Check if all characters are digits
        if (!tcKimlik.matches("\\d+")) {
            return false;
        }

        // First digit cannot be 0
        if (tcKimlik.charAt(0) == '0') {
            return false;
        }

        // Convert to int array
        int[] digits = new int[TC_KIMLIK_LENGTH];
        for (int i = 0; i < TC_KIMLIK_LENGTH; i++) {
            digits[i] = Character.getNumericValue(tcKimlik.charAt(i));
        }

        // Calculate sum of first 10 digits
        int sumFirst10 = 0;
        for (int i = 0; i < 10; i++) {
            sumFirst10 += digits[i];
        }

        // Check if sum % 10 equals 11th digit
        if (sumFirst10 % 10 != digits[10]) {
            return false;
        }

        // Calculate (sum of odd positions * 7 - sum of even positions) % 10
        int sumOdd = digits[0] + digits[2] + digits[4] + digits[6] + digits[8];
        int sumEven = digits[1] + digits[3] + digits[5] + digits[7];
        int calculated = (sumOdd * 7 - sumEven) % 10;

        // Check if calculated value equals 10th digit
        return calculated == digits[9];
    }

    /**
     * Validate email address
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate Turkish phone number
     * Accepts formats: 05XXXXXXXXX, 5XXXXXXXXX
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        
        // Remove spaces, dashes, and parentheses
        String cleanPhone = phone.replaceAll("[\\s\\-()]", "");
        
        // If starts with 0, check pattern
        if (cleanPhone.startsWith("0")) {
            return PHONE_PATTERN.matcher(cleanPhone).matches();
        }
        
        // If doesn't start with 0, add it and check
        if (cleanPhone.startsWith("5") && cleanPhone.length() == 10) {
            return PHONE_PATTERN.matcher("0" + cleanPhone).matches();
        }
        
        return false;
    }

    /**
     * Format phone number to standard format (05XX XXX XX XX)
     */
    public static String formatPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return null;
        }
        
        String cleanPhone = phone.replaceAll("[\\s\\-()]", "");
        
        if (!cleanPhone.startsWith("0")) {
            cleanPhone = "0" + cleanPhone;
        }
        
        if (cleanPhone.length() == 11) {
            return String.format("%s %s %s %s",
                    cleanPhone.substring(0, 4),
                    cleanPhone.substring(4, 7),
                    cleanPhone.substring(7, 9),
                    cleanPhone.substring(9, 11));
        }
        
        return phone;
    }

    /**
     * Validate barcode
     */
    public static boolean isValidBarcode(String barcode) {
        if (barcode == null || barcode.isEmpty()) {
            return false;
        }
        return BARCODE_PATTERN.matcher(barcode).matches();
    }

    /**
     * Validate password strength
     * Rules:
     * - At least 8 characters
     * - Contains at least one uppercase letter
     * - Contains at least one lowercase letter
     * - Contains at least one digit
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasUpper && hasLower && hasDigit;
    }

    /**
     * Validate stock quantity (must be non-negative)
     */
    public static boolean isValidStockQuantity(Integer quantity) {
        return quantity != null && quantity >= 0;
    }

    /**
     * Validate minimum stock level (must be positive)
     */
    public static boolean isValidMinimumStockLevel(Integer level) {
        return level != null && level > 0;
    }

    /**
     * Sanitize string input (remove potentially harmful characters)
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Remove HTML tags and special characters that could be used for injection
        return input.replaceAll("<[^>]*>", "")
                   .replaceAll("[<>\"'%;()&+]", "")
                   .trim();
    }

    /**
     * Check if string is not null and not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validate dosage format (e.g., "1 tablet", "500mg", "2x1")
     */
    public static boolean isValidDosage(String dosage) {
        if (dosage == null || dosage.isEmpty()) {
            return false;
        }
        // Basic validation - can be extended based on requirements
        return dosage.length() <= 50;
    }

    /**
     * Validate frequency format (e.g., "Günde 2 kez", "Sabah-Akşam")
     */
    public static boolean isValidFrequency(String frequency) {
        if (frequency == null || frequency.isEmpty()) {
            return false;
        }
        // Basic validation - can be extended based on requirements
        return frequency.length() <= 100;
    }
}