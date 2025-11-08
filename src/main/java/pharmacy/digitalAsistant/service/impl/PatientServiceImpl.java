package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.domain.entity.Patient;
import pharmacy.digitalAsistant.dto.mapper.PatientMapper;
import pharmacy.digitalAsistant.dto.request.PatientRequestDTO;
import pharmacy.digitalAsistant.dto.response.PatientResponseDTO;
import pharmacy.digitalAsistant.repository.PatientRepository;
import pharmacy.digitalAsistant.service.abstracts.PatientService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper mapper;

    @Override
    public PatientResponseDTO register(PatientRequestDTO dto) {
        Patient entity = mapper.toEntity(dto);
        Patient saved = patientRepository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public PatientResponseDTO getById(Long id) {
        return mapper.toDTO(patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found")));
    }

    @Override
    public List<PatientResponseDTO> search(String q) {
        return patientRepository.search(q).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> getAll() {
        return patientRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
