package pucminas.listatarefas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import pucminas.listatarefas.enums.Priority;

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
    @Column(name = ID, unique = true, nullable = false, updatable = false)
    private UUID id;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = MSG_NAME_ERROR)
    @Column(name = NAME, nullable = false)
    private String name;

    @Column(name = DESCRIPTION)
    private String description;

    @Column(name = PRIORITY, nullable = false)
    private Priority priority;

    @JsonFormat(pattern = DATE_PATTERN)
    @Column(name = DUE_DATE, nullable = false)
    private LocalDateTime dueDate;

    @JsonProperty(COMPLETED_DATE)
    @JsonFormat(pattern = DATE_PATTERN)
    @Column(name = COMPLETED_DATE)
    private LocalDateTime completedDate;

    @JsonProperty(CREATE_DATE)
    @JsonFormat(pattern = DATE_PATTERN)
    @Column(name = CREATE_DATE, nullable = false, updatable = false)
    private LocalDateTime createDate;

    @JsonProperty(LAST_MODIFIED_DATE)
    @JsonFormat(pattern = DATE_PATTERN)
    @Column(name = LAST_MODIFIED_DATE, nullable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = IS_COMPLETED, nullable = false)
    @JsonProperty(IS_COMPLETED)
    private boolean isCompleted;
}
