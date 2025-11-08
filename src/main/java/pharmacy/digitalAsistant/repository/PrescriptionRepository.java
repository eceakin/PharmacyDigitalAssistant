package pharmacy.digitalAsistant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import pharmacy.digitalAsistant.domain.entity.Prescription;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatient_Id(Long patientId);

    @Query("SELECT p FROM Prescription p WHERE p.expiryDate BETWEEN :start AND :end")
    List<Prescription> findExpiringBetween(@Param("start") LocalDate start,
                                           @Param("end") LocalDate end);

    @Query("SELECT p FROM Prescription p WHERE p.expiryDate <= :date")
    List<Prescription> findExpiredBefore(@Param("date") LocalDate date);
}
