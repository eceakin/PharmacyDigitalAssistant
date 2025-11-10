package pharmacy.digitalAsistant.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medications", indexes = {
    @Index(name = "idx_barcode", columnList = "barcode"),
    @Index(name = "idx_medication_name", columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medication extends BaseEntity {

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "barcode", unique = true, length = 50)
    private String barcode;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "active_ingredient", length = 200)
    private String activeIngredient;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventoryList = new ArrayList<>();

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionMedication> prescriptionMedications = new ArrayList<>();
}