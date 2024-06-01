package pucminas.todolist.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pucminas.todolist.dto.TaskDTO;
import pucminas.todolist.entity.Task;

import java.util.UUID;

/**
 * Especialização da interface de Controller de Task
 * Recebe como entrada (input) objetos do tipo Task e retorna (output) objetos do tipo TaskDTO
 */
public interface TaskController extends CrudController<Task, TaskDTO> {

    @PatchMapping("/{id}")
    ResponseEntity<TaskDTO> updateIsCompletedStatus(@PathVariable UUID id);
}
