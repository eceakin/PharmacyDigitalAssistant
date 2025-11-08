package pharmacy.digitalAsistant.domain.entity;


import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient extends BaseEntity {

    @NotBlank
    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String nationalId; // or another identifier

    private LocalDate birthDate;

    private String phone;

    private String email;

    private String address;

    // chronic conditions, allergies, notes — keep simple text for now
    @Column(columnDefinition = "text")
    private String chronicConditions;

    @Column(columnDefinition = "text")
    private String allergies;

    // optional link to a user account
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // prescriptions (one to many)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions;

    // notifications sent to this patient
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;
}
