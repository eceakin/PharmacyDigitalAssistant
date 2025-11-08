package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import pharmacy.digitalAsistant.domain.entity.Notification;
import pharmacy.digitalAsistant.domain.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByPatient_Id(Long patientId);

    @Query("SELECT n FROM Notification n WHERE n.status = :status")
    List<Notification> findByStatus(@Param("status") NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.status = com.pharmacy.eczasistan.domain.enums.NotificationStatus.PENDING AND n.scheduledAt <= :now")
    List<Notification> findPendingToSend(@Param("now") LocalDateTime now);

    @Query("SELECT n FROM Notification n WHERE n.status = com.pharmacy.eczasistan.domain.enums.NotificationStatus.FAILED")
    List<Notification> findFailedNotifications();
}
