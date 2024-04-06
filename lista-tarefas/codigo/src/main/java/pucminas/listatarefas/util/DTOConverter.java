package pucminas.listatarefas.util;

import lombok.experimental.UtilityClass;
import pucminas.listatarefas.controller.interfaces.TaskController;
import pucminas.listatarefas.dto.TaskDTO;
import pucminas.listatarefas.entity.Task;

import static java.util.Collections.singletonList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pucminas.listatarefas.util.constants.Constants.TASK_ENDPOINT;

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
                .daysToComplete(task.getDaysToComplete())
                .completedDate(task.getCompletedDate())
                .isCompleted(task.isCompleted())
                .links(singletonList(linkTo(TaskController.class).slash(TASK_ENDPOINT).slash(task.getId()).withSelfRel()))
                .build();
    }
}
