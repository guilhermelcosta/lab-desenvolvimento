package pucminas.listatarefas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import pucminas.listatarefas.enums.Priority;
import pucminas.listatarefas.enums.Tag;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static pucminas.listatarefas.util.Constants.*;

@Data
@Entity
@Builder
@Table(name = TB_TASK)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Classe que representa a classe de tarefa do projeto")
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID da tarefa")
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
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tag (classificação) da tarefa")
    @Column(name = TAG)
    private Tag tag;

    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data de finalização da tarefa")
    @Column(name = DUE_DATE)
    private LocalDateTime dueDate;

    @JsonProperty(COMPLETED_DATE)
    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data que a tarefa foi concluída")
    @Column(name = COMPLETED_DATE)
    private LocalDateTime completedDate;

    @JsonProperty(CREATE_DATE)
    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data de criação da tarefa")
    @Column(name = CREATE_DATE, nullable = false, updatable = false)
    private LocalDateTime createDate;

    @JsonProperty(LAST_MODIFIED_DATE)
    @JsonFormat(pattern = DATE_PATTERN)
    @Schema(description = "Data de última modificação da tarefa")
    @Column(name = LAST_MODIFIED_DATE, nullable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = IS_COMPLETED, nullable = false)
    @Schema(description = "Atributo que indica se a tarefa já foi ou não concluída")
    @JsonProperty(IS_COMPLETED)
    private boolean isCompleted;
}
