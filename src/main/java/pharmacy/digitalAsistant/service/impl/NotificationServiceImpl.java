package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import pharmacy.digitalAsistant.domain.entity.Notification;
import pharmacy.digitalAsistant.domain.enums.NotificationStatus;
import pharmacy.digitalAsistant.dto.mapper.NotificationMapper;
import pharmacy.digitalAsistant.dto.request.NotificationRequestDTO;
import pharmacy.digitalAsistant.dto.response.NotificationResponseDTO;
import pharmacy.digitalAsistant.repository.NotificationRepository;
import pharmacy.digitalAsistant.repository.PatientRepository;
import pharmacy.digitalAsistant.service.abstracts.NotificationService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final PatientRepository patientRepository;
    private final NotificationMapper mapper;

    @Override
    public NotificationResponseDTO create(NotificationRequestDTO dto) {

        patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Notification entity = mapper.toEntity(dto);
        entity.setStatus(NotificationStatus.PENDING);

        Notification saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public List<NotificationResponseDTO> getPendingToSend() {
        return repository.findPendingToSend(LocalDateTime.now()).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void markAsSent(Long id) {
        Notification n = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setStatus(NotificationStatus.SENT);
        n.setSentAt(LocalDateTime.now());
        repository.save(n);
    }
}
