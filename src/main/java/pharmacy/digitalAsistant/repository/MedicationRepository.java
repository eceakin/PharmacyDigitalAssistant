package pharmacy.digitalAsistant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import pharmacy.digitalAsistant.domain.entity.Medication;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findByNameContainingIgnoreCase(String name);

    @Query("SELECT m FROM Medication m WHERE lower(m.name) LIKE lower(concat('%', :q, '%')) " +
           "OR lower(m.genericName) LIKE lower(concat('%', :q, '%'))")
    List<Medication> searchByNameOrGeneric(@Param("q") String q);

    Optional<Medication> findByName(String name);
}
