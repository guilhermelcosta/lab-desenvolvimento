package pucminas.todolist.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import pucminas.todolist.controller.interfaces.TaskController;
import pucminas.todolist.dto.TaskDTO;
import pucminas.todolist.entity.Task;

import static java.util.Collections.singletonList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pucminas.todolist.util.StatusVerifier.verifyStatus;
import static pucminas.todolist.util.constants.Constants.TASK_ENDPOINT;

@UtilityClass
public class DTOConverter {

    /**
     * Converte um objeto Task em TaskDTO
     *
     * @param task objeto do tipo Task
     * @return TaskDTO
     */
    public static TaskDTO convertToDTO(@NotNull Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(verifyStatus(task))
                .tag(task.getTag())
                .dueTime(task.getDueDate())
                .daysToComplete(task.getDaysToComplete())
                .completedDate(task.getCompletedDate())
                .isCompleted(task.isCompleted())
                .links(singletonList(linkTo(TaskController.class).slash(TASK_ENDPOINT).slash(task.getId()).withSelfRel()))
                .build();
    }
}
