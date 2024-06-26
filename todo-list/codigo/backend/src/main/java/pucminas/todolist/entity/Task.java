package pucminas.todolist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import pucminas.todolist.enums.Priority;
import pucminas.todolist.enums.Tag;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static pucminas.todolist.enums.Priority.NO_PRIORITY;
import static pucminas.todolist.util.constants.Constants.*;

@Data
@Entity
@Builder
@Table(name = TB_TASK)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Classe que representa a classe de tarefa do projeto")
public class Task extends RepresentationModel<Task> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID da tarefa")
    @JsonIgnore
    @Column(name = ID, unique = true, nullable = false, updatable = false)
    private UUID id;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = MSG_NAME_ERROR)
    @Schema(description = "Título da tarefa, não pode ser utilizado caracteres especias como, exemplo: @!-")
    @Column(name = TITLE, nullable = false)
    private String title;

    @Column(name = DESCRIPTION)
    @Schema(description = "Descrição da tarefa")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Prioridade da tarefa")
    @Column(name = PRIORITY, nullable = false)
    private Priority priority = NO_PRIORITY;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tag (classificação) da tarefa")
    @Column(name = TAG)
    private Tag tag;

    @JsonProperty(DUE_DATE)
    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data de finalização da tarefa")
    @Column(name = DUE_DATE)
    private LocalDateTime dueDate;

    @JsonProperty(DAYS_TO_COMPLETE)
    @Schema(description = "Quantidade de dias para concluir a tarefa (prazo)")
    @Column(name = DAYS_TO_COMPLETE)
    private Integer daysToComplete;

    @JsonProperty(COMPLETED_DATE_JSON)
    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data que a tarefa foi concluída")
    @JsonIgnore
    @Column(name = COMPLETED_DATE_JSON)
    private LocalDateTime completedDate;

    @JsonProperty(CREATE_DATE_JSON)
    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data de criação da tarefa")
    @JsonIgnore
    @Column(name = CREATE_DATE_JSON, nullable = false, updatable = false)
    private LocalDateTime createDate;

    @JsonProperty(LAST_MODIFIED_DATE_JSON)
    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data de última modificação da tarefa")
    @JsonIgnore
    @Column(name = LAST_MODIFIED_DATE_JSON, nullable = false)
    private LocalDateTime lastModifiedDate;

    @Schema(description = "Atributo que indica se a tarefa já foi ou não concluída")
    @JsonProperty(IS_COMPLETED_JSON)
    @JsonIgnore
    @Column(name = IS_COMPLETED_JSON, nullable = false)
    private boolean isCompleted;
}
