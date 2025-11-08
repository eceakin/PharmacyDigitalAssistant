package pharmacy.digitalAsistant.domain.entity;


import lombok.*;
import pharmacy.digitalAsistant.domain.enums.NotificationStatus;
import pharmacy.digitalAsistant.domain.enums.NotificationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String title;

    @Column(columnDefinition = "text")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    // who triggered this (user id or system)
    private Long performedBy;
}
