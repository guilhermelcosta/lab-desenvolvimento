package pucminas.listatarefas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.hateoas.Link;
import pucminas.listatarefas.enums.Priority;
import pucminas.listatarefas.enums.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Schema(description = "Classe de apresentação da classe Task")
public record TaskDTO(UUID id,
                      String title,
                      String description,
                      Priority priority,
                      Tag tag,
                      String status,
                      LocalDateTime dueTime,
                      Integer daysToComplete,
                      LocalDateTime completedDate,
                      boolean isCompleted,
                      List<Link> links) {
}
