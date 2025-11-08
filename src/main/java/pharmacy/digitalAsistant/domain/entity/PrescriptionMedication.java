package pharmacy.digitalAsistant.domain.entity;


import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "prescription_medications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionMedication extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    // quantity prescribed (e.g., number of units)
    @Min(1)
    private Integer quantity;

    // e.g. "1 tablet twice a day"
    private String dosageInstructions;

    // optional notes (substitutions, restrictions)
    @Column(columnDefinition = "text")
    private String notes;
}
