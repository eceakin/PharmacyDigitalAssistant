package pharmacy.digitalAsistant.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescriptions", indexes = {
		@Index(name = "idx_prescriptions_patient_id", columnList = "patient_id"),
		@Index(name = "idx_prescriptions_expiry_date", columnList = "expiry_date"),
		@Index(name = "idx_prescriptions_number", columnList = "prescription_number")

})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "prescription_number", unique = true, length = 100)
    private String prescriptionNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "doctor_name", length = 100)
    private String doctorName;

    @Column(name = "hospital_name", length = 200)
    private String hospitalName;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionMedication> prescriptionMedications = new ArrayList<>();

    // Helper method to check if prescription is expired
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    // Helper method to check if expiring soon (within days)
    public boolean isExpiringSoon(int days) {
        LocalDate targetDate = LocalDate.now().plusDays(days);
        return expiryDate.isBefore(targetDate) && !isExpired();
    }

    // Helper method to add medication
    public void addMedication(PrescriptionMedication prescriptionMedication) {
        prescriptionMedications.add(prescriptionMedication);
        prescriptionMedication.setPrescription(this);
    }

    // Helper method to remove medication
    public void removeMedication(PrescriptionMedication prescriptionMedication) {
        prescriptionMedications.remove(prescriptionMedication);
        prescriptionMedication.setPrescription(null);
    }
}