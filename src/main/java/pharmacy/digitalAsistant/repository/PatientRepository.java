package pharmacy.digitalAsistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import pharmacy.digitalAsistant.domain.entity.Patient;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByNationalId(String nationalId);

    @Query("SELECT p FROM Patient p WHERE " +
           "(:q IS NULL OR lower(p.firstName) LIKE lower(concat('%', :q, '%')) " +
           "OR lower(p.lastName) LIKE lower(concat('%', :q, '%')) " +
           "OR lower(p.phone) LIKE lower(concat('%', :q, '%')) " +
           "OR lower(p.email) LIKE lower(concat('%', :q, '%')) )")
    List<Patient> search(@Param("q") String query);
}
