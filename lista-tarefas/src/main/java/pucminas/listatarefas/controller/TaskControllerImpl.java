package pucminas.listatarefas.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    /**
     * Rota para encontrar uma tarefa por id
     *
     * @param id id da tarefa
     * @return tarefa encontrada
     */
//    todo: finalizar documentação da API (description)
    @Operation(summary = "Rota para encontrar uma tarefa por id", description = "Teste")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Task> findById(@PathVariable UUID id) {
        log.info("TaskControllerImpl - findById: recebendo requisição para encontrar tarefa por id");
        Task task = taskService.findById(id);
        return ResponseEntity.ok().body(task);
    }

    /**
     * Rota para listar todas as tarefas cadastradas
     *
     * @return todas as tarefas cadastradas
     */
    @Operation(summary = "Rota para listar todas as tarefas cadastradas")
    @GetMapping
    @Override
    public ResponseEntity<List<Task>> listAll() {
        log.info("TaskControllerImpl - listAll: recebendo requisição para listar todas as tarefas cadastradas");
        List<Task> taskList = taskService.listAll();
        return ResponseEntity.ok().body(taskList);
    }

    /**
     * Rota para criar uma nova tarefa
     *
     * @param task objeto do tipo Tarefa
     * @return tarefa criada
     */
    @Operation(summary = "Rota para criar uma nova tarefa")
    @PostMapping
    @Override
    public ResponseEntity<Task> create(@RequestBody Task task) {
        log.info("TaskControllerImpl - create: recebendo requisição para criar tarefa");
        Task createdTask = taskService.create(task);
        return ResponseEntity.ok().body(createdTask);
    }

    /**
     * Rota para atualizar uma tarefa
     *
     * @param task objeto do tipo Tarefa
     * @return tarefa atualizada
     */
    @Operation(summary = "Rota para atualizar uma tarefa")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Task> update(@PathVariable UUID id, Task task) {
        log.info("TaskControllerImpl - findById: recebendo requisição para atualizar tarefa");
        task.setId(id);
        Task updatedTask = taskService.update(task);
        return ResponseEntity.ok().body(updatedTask);
    }

    /**
     * Rota para deletar uma tarefa
     *
     * @param id id da tarefa
     * @return tarefa deletada
     */
    @Operation(summary = "Rota para deletar uma tarefa")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        log.info("TaskControllerImpl - delete: recebendo requisição para deletar tarefa");
        taskService.delete(id);
        return ResponseEntity.ok().body("Deletado");
    }

    /**
     * Rota para atualizar o status de 'isCompleted' da tarefa
     *
     * @param id id da tarefa
     * @return tarefa atualizada
     */
    @Operation(summary = "Rota para atualizar o status de 'isCompleted' da tarefa")
    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<Task> updateIsCompletedStatus(@PathVariable UUID id) {
        log.info("TaskControllerImpl - updateIsCompletedStatus: recebendo requisição para alterar status da tarefa");
        Task updatedTask = taskService.updateIsCompletedStatus(id);
        return ResponseEntity.ok().body(updatedTask);
    }
}
