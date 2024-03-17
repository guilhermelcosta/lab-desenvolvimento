package pucminas.listatarefas.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pucminas.listatarefas.controller.interfaces.TaskController;
import pucminas.listatarefas.entity.Task;
import pucminas.listatarefas.service.interfaces.TaskService;

import java.util.List;
import java.util.UUID;

import static pucminas.listatarefas.util.Constants.TASK_ENDPOINT;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(TASK_ENDPOINT)
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public Task findById(UUID id) {
        return null;
    }

    @Operation(summary = "Lista todas as tarefas cadastradas")
    @GetMapping()
    public List<Task> listAll() {
        return null;
    }

    @PostMapping()
    public Task create(@RequestBody Task task) {
        log.info("TaskControllerImpl - create: recebendo requisição para criar tarefa");
        return taskService.create(task);
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    @PatchMapping("/{id}")
    public Task changeIsComplete(@PathVariable UUID id) {
       log.info("TaskControllerImpl - changeIsComplete: recebendo requisição para alterar status da tarefa");
        return taskService.changeIsComplete(id);
    }
}
