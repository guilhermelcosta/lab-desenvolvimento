package pucminas.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.hateoas.Link;
import pucminas.todolist.enums.Priority;
import pucminas.todolist.enums.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static pucminas.todolist.util.constants.Constants.*;

@Builder
@Schema(description = "Classe de apresentação da classe Task")
public record TaskDTO(UUID id,
                      String title,
                      String description,
                      Priority priority,
                      Tag tag,
                      String status,
                      @JsonProperty(DUE_DATE)
                      LocalDateTime dueDate,
                      @JsonProperty(DAYS_TO_COMPLETE)
                      Integer daysToComplete,
                      @JsonProperty(COMPLETED_DATE_JSON)
                      LocalDateTime completedDate,
                      @JsonProperty(IS_COMPLETED_JSON)
                      boolean isCompleted,
                      List<Link> links) {
}
