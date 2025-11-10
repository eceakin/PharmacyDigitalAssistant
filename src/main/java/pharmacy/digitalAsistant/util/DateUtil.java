package pharmacy.digitalAsistant.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Format LocalDate to string (dd.MM.yyyy)
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    /**
     * Format LocalDateTime to string (dd.MM.yyyy HH:mm)
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * Format LocalDateTime to time string (HH:mm)
     */
    public static String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * Parse string to LocalDate (dd.MM.yyyy)
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    /**
     * Parse string to LocalDateTime (dd.MM.yyyy HH:mm)
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
    }

    /**
     * Calculate days between two dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Calculate months between two dates
     */
    public static long monthsBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.MONTHS.between(startDate, endDate);
    }

    /**
     * Check if date is within range
     */
    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null || startDate == null || endDate == null) {
            return false;
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Get date N days from now
     */
    public static LocalDate addDays(int days) {
        return LocalDate.now().plusDays(days);
    }

    /**
     * Get date N days from specific date
     */
    public static LocalDate addDays(LocalDate date, int days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }

    /**
     * Get date N months from now
     */
    public static LocalDate addMonths(int months) {
        return LocalDate.now().plusMonths(months);
    }

    /**
     * Get date N months from specific date
     */
    public static LocalDate addMonths(LocalDate date, int months) {
        if (date == null) {
            return null;
        }
        return date.plusMonths(months);
    }

    /**
     * Check if date is today
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.equals(LocalDate.now());
    }

    /**
     * Check if date is in the past
     */
    public static boolean isPast(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDate.now());
    }

    /**
     * Check if date is in the future
     */
    public static boolean isFuture(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isAfter(LocalDate.now());
    }

    /**
     * Check if date is expiring within N days
     */
    public static boolean isExpiringWithinDays(LocalDate expiryDate, int days) {
        if (expiryDate == null) {
            return false;
        }
        LocalDate targetDate = LocalDate.now().plusDays(days);
        return !expiryDate.isAfter(targetDate) && !expiryDate.isBefore(LocalDate.now());
    }

    /**
     * Check if date is expiring within N months
     */
    public static boolean isExpiringWithinMonths(LocalDate expiryDate, int months) {
        if (expiryDate == null) {
            return false;
        }
        LocalDate targetDate = LocalDate.now().plusMonths(months);
        return !expiryDate.isAfter(targetDate) && !expiryDate.isBefore(LocalDate.now());
    }

    /**
     * Get start of day
     */
    public static LocalDateTime startOfDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atStartOfDay();
    }

    /**
     * Get end of day
     */
    public static LocalDateTime endOfDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atTime(23, 59, 59);
    }

    /**
     * Get relative time description (e.g., "3 gün önce", "2 saat sonra")
     */
    public static String getRelativeTimeDescription(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(now, dateTime);
        long hours = ChronoUnit.HOURS.between(now, dateTime);
        long days = ChronoUnit.DAYS.between(now, dateTime);

        if (Math.abs(minutes) < 60) {
            return Math.abs(minutes) + (minutes < 0 ? " dakika önce" : " dakika sonra");
        } else if (Math.abs(hours) < 24) {
            return Math.abs(hours) + (hours < 0 ? " saat önce" : " saat sonra");
        } else {
            return Math.abs(days) + (days < 0 ? " gün önce" : " gün sonra");
        }
    }
}