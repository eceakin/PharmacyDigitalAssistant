package pharmacy.digitalAsistant.service.abstracts;


import pharmacy.digitalAsistant.dto.request.MedicationRequest;
import pharmacy.digitalAsistant.dto.response.MedicationResponse;
import java.util.List;

public interface MedicationService {
    MedicationResponse createMedication(MedicationRequest request);
    MedicationResponse updateMedication(Long id, MedicationRequest request);
    MedicationResponse getMedicationById(Long id);
    List<MedicationResponse> getAllMedications();
    void deleteMedication(Long id);
    List<MedicationResponse> searchMedications(String keyword);
    boolean existsByBarcode(String barcode);
}
