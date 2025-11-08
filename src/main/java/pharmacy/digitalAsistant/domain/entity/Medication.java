package pharmacy.digitalAsistant.domain.entity;


import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "medications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication extends BaseEntity {

    @NotBlank
    private String name;

    private String genericName;

    private String manufacturer;

    private String form; // e.g., tablet, syrup

    private String strength; // e.g., "500 mg"

    @Column(columnDefinition = "text")
    private String description;

    // prescriptions - many-to-many via PrescriptionMedication
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionMedication> prescriptionMedications;

    // inventories referencing this medication (one medication can have multiple inventory lots)
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories;
}
