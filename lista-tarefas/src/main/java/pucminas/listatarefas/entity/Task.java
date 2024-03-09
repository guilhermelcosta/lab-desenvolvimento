package pucminas.listatarefas.entity;

import lombok.*;
import pucminas.listatarefas.enums.Priority;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String description;
    private Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime completedDate;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private boolean completed;
}
