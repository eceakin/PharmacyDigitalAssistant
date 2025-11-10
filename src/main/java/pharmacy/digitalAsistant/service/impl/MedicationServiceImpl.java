package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacy.digitalAsistant.domain.entity.Medication;
import pharmacy.digitalAsistant.dto.request.MedicationRequest;
import pharmacy.digitalAsistant.dto.response.MedicationResponse;
import pharmacy.digitalAsistant.exception.ResourceNotFoundException;
import pharmacy.digitalAsistant.exception.ValidationException;
import pharmacy.digitalAsistant.repository.MedicationRepository;
import pharmacy.digitalAsistant.service.abstracts.MedicationService;
import pharmacy.digitalAsistant.util.ValidationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    @Override
    public MedicationResponse createMedication(MedicationRequest request) {
        log.info("Creating medication: {}", request.getName());

        if (request.getBarcode() != null && !ValidationUtil.isValidBarcode(request.getBarcode())) {
            throw new ValidationException("Geçersiz barkod formatı");
        }

        if (request.getBarcode() != null && medicationRepository.existsByBarcode(request.getBarcode())) {
            throw new ValidationException("Bu barkod zaten kayıtlı");
        }

        Medication medication = new Medication();
        medication.setName(request.getName());
        medication.setBarcode(request.getBarcode());
        medication.setSerialNumber(request.getSerialNumber());
        medication.setDescription(request.getDescription());
        medication.setActiveIngredient(request.getActiveIngredient());
        medication.setManufacturer(request.getManufacturer());

        Medication saved = medicationRepository.save(medication);
        log.info("Medication created with ID: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    public MedicationResponse updateMedication(Long id, MedicationRequest request) {
        log.info("Updating medication ID: {}", id);

        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("İlaç bulunamadı: " + id));

        if (request.getBarcode() != null && !medication.getBarcode().equals(request.getBarcode())) {
            if (!ValidationUtil.isValidBarcode(request.getBarcode())) {
                throw new ValidationException("Geçersiz barkod formatı");
            }
            if (medicationRepository.existsByBarcode(request.getBarcode())) {
                throw new ValidationException("Bu barkod zaten kayıtlı");
            }
        }

        medication.setName(request.getName());
        medication.setBarcode(request.getBarcode());
        medication.setSerialNumber(request.getSerialNumber());
        medication.setDescription(request.getDescription());
        medication.setActiveIngredient(request.getActiveIngredient());
        medication.setManufacturer(request.getManufacturer());

        Medication updated = medicationRepository.save(medication);
        log.info("Medication updated with ID: {}", updated.getId());
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicationResponse getMedicationById(Long id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("İlaç bulunamadı: " + id));
        return mapToResponse(medication);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationResponse> getAllMedications() {
        return medicationRepository.findAllByOrderByNameAsc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMedication(Long id) {
        if (!medicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("İlaç bulunamadı: " + id);
        }
        medicationRepository.deleteById(id);
        log.info("Medication deleted with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationResponse> searchMedications(String keyword) {
        return medicationRepository.searchMedications(keyword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBarcode(String barcode) {
        return medicationRepository.existsByBarcode(barcode);
    }

    private MedicationResponse mapToResponse(Medication medication) {
        MedicationResponse response = new MedicationResponse();
        response.setId(medication.getId());
        response.setName(medication.getName());
        response.setBarcode(medication.getBarcode());
        response.setSerialNumber(medication.getSerialNumber());
        response.setDescription(medication.getDescription());
        response.setActiveIngredient(medication.getActiveIngredient());
        response.setManufacturer(medication.getManufacturer());
        response.setCreatedAt(medication.getCreatedAt());
        response.setUpdatedAt(medication.getUpdatedAt());
        return response;
    }
}
