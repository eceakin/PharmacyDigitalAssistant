package pharmacy.digitalAsistant.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "prescription_medications", indexes = {
		@Index(name = "idx_prescription_meds_medication_id", columnList = "medication_id"),
		@Index(name = "idx_prescription_meds_prescription_id", columnList = "prescription_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionMedication extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name = "dosage", nullable = false, length = 100)
    private String dosage; // e.g., "1 tablet", "500mg"

    @Column(name = "frequency", nullable = false, length = 100)
    private String frequency; // e.g., "Günde 2 kez", "Sabah-Akşam"

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays; // e.g., 30, 90

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions; // e.g., "Tok karna alınız"

    @Column(name = "quantity_per_day")
    private Integer quantityPerDay; // Total tablets/doses per day

    // Helper method to check if medication period is active
    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }

    // Helper method to check if ending soon (within days)
    public boolean isEndingSoon(int days) {
        LocalDate targetDate = LocalDate.now().plusDays(days);
        return endDate.isBefore(targetDate) && !endDate.isBefore(LocalDate.now());
    }

    // Helper method to calculate remaining days
    public long getRemainingDays() {
        if (endDate.isBefore(LocalDate.now())) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
}