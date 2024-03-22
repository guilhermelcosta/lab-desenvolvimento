package pucminas.listatarefas.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import pucminas.listatarefas.dto.TaskDTO;
import pucminas.listatarefas.entity.Task;

import java.util.UUID;

public interface TaskController extends CrudController<TaskDTO> {

    @PatchMapping("/{id}")
    ResponseEntity<TaskDTO> updateIsCompletedStatus(UUID id);
}
