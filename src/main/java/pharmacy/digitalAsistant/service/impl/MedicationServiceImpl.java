package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.domain.entity.Medication;
import pharmacy.digitalAsistant.dto.mapper.MedicationMapper;
import pharmacy.digitalAsistant.dto.request.MedicationRequestDTO;
import pharmacy.digitalAsistant.dto.response.MedicationResponseDTO;
import pharmacy.digitalAsistant.repository.MedicationRepository;
import pharmacy.digitalAsistant.service.abstracts.MedicationService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository repository;
    private final MedicationMapper mapper;

    @Override
    public MedicationResponseDTO create(MedicationRequestDTO dto) {
        Medication saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    @Override
    public MedicationResponseDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found")));
    }

    @Override
    public List<MedicationResponseDTO> search(String name) {
        return repository.searchByNameOrGeneric(name).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<MedicationResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
