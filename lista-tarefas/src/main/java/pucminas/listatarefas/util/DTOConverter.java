package pucminas.listatarefas.util;

import lombok.experimental.UtilityClass;
import pucminas.listatarefas.dto.TaskDTO;
import pucminas.listatarefas.entity.Task;

@UtilityClass
public class DTOConverter {

    /**
     * Converte um objeto Task em TaskDTO
     *
     * @param task objeto do tipo Task
     * @return TaskDTO
     */
    public static TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .tag(task.getTag())
                .dueTime(task.getDueDate())
                .completedDate(task.getCompletedDate())
                .isCompleted(task.isCompleted())
                .build();
    }
}
