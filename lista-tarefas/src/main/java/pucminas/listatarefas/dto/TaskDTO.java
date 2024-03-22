package pucminas.listatarefas.dto;

import lombok.Builder;
import pucminas.listatarefas.enums.Priority;
import pucminas.listatarefas.enums.Tag;

import java.time.LocalDateTime;

@Builder
public record TaskDTO(String title,
                      String description,
                      Priority priority,
                      Tag tag,
                      LocalDateTime dueTime,
                      LocalDateTime completedDate,
                      boolean isCompleted) {
}
