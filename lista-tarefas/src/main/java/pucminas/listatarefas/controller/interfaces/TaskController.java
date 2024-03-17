package pucminas.listatarefas.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import pucminas.listatarefas.entity.Task;

import java.util.UUID;

public interface TaskController extends CrudController<Task> {

    @PatchMapping("/{id}")
    ResponseEntity<Task> updateIsCompletedStatus(UUID id);
}
