package pucminas.listatarefas.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pucminas.listatarefas.entity.Task;
import pucminas.listatarefas.service.interfaces.TaskService;

import java.util.UUID;

import static pucminas.listatarefas.util.Constants.TASK_ENDPOINT;

@AllArgsConstructor
@RestController
@RequestMapping(TASK_ENDPOINT)
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Lista todas as tarefas cadastradas")
    @GetMapping()
    public String listAll() {
        return "Listando todas as tarefas...";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable UUID id) {
        return "Obtendo tarefa...";
    }

    @PostMapping()
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable UUID id, Task task) {
        return "Atualizando tarefa...";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return "Deletando tarefa...";
    }
}
