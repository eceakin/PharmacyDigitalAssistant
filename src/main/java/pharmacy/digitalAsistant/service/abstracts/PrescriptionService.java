package pharmacy.digitalAsistant.service.abstracts;


import pharmacy.digitalAsistant.dto.request.PrescriptionRequest;
import pharmacy.digitalAsistant.dto.response.PrescriptionResponse;
import java.util.List;

public interface PrescriptionService {
    PrescriptionResponse createPrescription(PrescriptionRequest request);
    PrescriptionResponse updatePrescription(Long id, PrescriptionRequest request);
    PrescriptionResponse getPrescriptionById(Long id);
    List<PrescriptionResponse> getAllPrescriptions();
    List<PrescriptionResponse> getPatientPrescriptions(Long patientId);
    List<PrescriptionResponse> getExpiringPrescriptions(int days);
    void deactivatePrescription(Long id);
}
