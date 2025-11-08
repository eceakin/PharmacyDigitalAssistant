package pharmacy.digitalAsistant.domain.entity;


import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // who issued it - freeform (doctor name) or link to system user if stored
    private String prescriberName;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    @Column(columnDefinition = "text")
    private String notes;

    // association to medications (bridge)
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionMedication> prescriptionMedications;
}
