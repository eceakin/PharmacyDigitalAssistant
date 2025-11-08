package pharmacy.digitalAsistant.dto.mapper;

import org.mapstruct.Mapper;

import pharmacy.digitalAsistant.domain.entity.Notification;
import pharmacy.digitalAsistant.dto.request.NotificationRequestDTO;
import pharmacy.digitalAsistant.dto.response.NotificationResponseDTO;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toEntity(NotificationRequestDTO dto);

    NotificationResponseDTO toDTO(Notification entity);
}
