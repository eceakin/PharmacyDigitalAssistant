package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy.digitalAsistant.domain.entity.Notification;
import pharmacy.digitalAsistant.domain.enums.NotificationStatus;
import pharmacy.digitalAsistant.domain.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Find all notifications for a patient
     */
    @Query("SELECT n FROM Notification n WHERE n.patient.id = :patientId " +
           "ORDER BY n.createdAt DESC")
    List<Notification> findByPatientId(@Param("patientId") Long patientId);

    /**
     * Find notifications by status
     */
    List<Notification> findByStatus(NotificationStatus status);

    /**
     * Find notifications by type
     */
    List<Notification> findByType(NotificationType type);

    /**
     * Find failed notifications that can be retried
     */
    @Query("SELECT n FROM Notification n WHERE n.status = 'FAILED' " +
           "AND n.retryCount < 3 ORDER BY n.createdAt ASC")
    List<Notification> findFailedNotificationsForRetry();

    /**
     * Find notifications sent between dates
     */
    @Query("SELECT n FROM Notification n WHERE n.sentAt BETWEEN :startDate AND :endDate " +
           "ORDER BY n.sentAt DESC")
    List<Notification> findBySentAtBetween(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /**
     * Find pending notifications scheduled before a certain time
     */
    @Query("SELECT n FROM Notification n WHERE n.status = 'PENDING' " +
           "AND n.scheduledFor <= :targetTime")
    List<Notification> findPendingNotificationsDue(@Param("targetTime") LocalDateTime targetTime);

    /**
     * Count notifications by status
     */
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.status = :status")
    long countByStatus(@Param("status") NotificationStatus status);

    /**
     * Count notifications for a patient
     */
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.patient.id = :patientId")
    long countByPatientId(@Param("patientId") Long patientId);

    /**
     * Find top N patients with most notifications
     */
    @Query("SELECT n.patient.id, n.patient.firstName, n.patient.lastName, COUNT(n) as notifCount " +
           "FROM Notification n " +
           "GROUP BY n.patient.id, n.patient.firstName, n.patient.lastName " +
           "ORDER BY notifCount DESC")
    List<Object[]> findTopNotifiedPatients();

    /**
     * Find recent notifications (last N days)
     */
    @Query("SELECT n FROM Notification n WHERE n.createdAt >= :sinceDate " +
           "ORDER BY n.createdAt DESC")
    List<Notification> findRecentNotifications(@Param("sinceDate") LocalDateTime sinceDate);

    /**
     * Count sent notifications in date range
     */
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.status = 'SENT' " +
           "AND n.sentAt BETWEEN :startDate AND :endDate")
    long countSentNotifications(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /**
     * Count failed notifications in date range
     */
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.status = 'FAILED' " +
           "AND n.sentAt BETWEEN :startDate AND :endDate")
    long countFailedNotifications(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}