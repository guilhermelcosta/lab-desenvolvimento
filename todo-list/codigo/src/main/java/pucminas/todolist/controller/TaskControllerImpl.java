package pucminas.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pucminas.todolist.controller.interfaces.TaskController;
import pucminas.todolist.dto.TaskDTO;
import pucminas.todolist.entity.Task;
import pucminas.todolist.service.interfaces.TaskService;
import pucminas.todolist.util.DTOConverter;

import java.util.List;
import java.util.UUID;

import static pucminas.todolist.util.DTOConverter.convertToDTO;
import static pucminas.todolist.util.constants.Constants.TASK_CONTROLLER;
import static pucminas.todolist.util.constants.Constants.TASK_ENDPOINT;

@Slf4j(topic = TASK_CONTROLLER)
@Validated
@AllArgsConstructor
@RestController
@RequestMapping(TASK_ENDPOINT)
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    /**
     * Rota para encontrar uma task por id
     *
     * @param id id da task
     * @return tarefa encontrada
     */
    @Operation(summary = "Rota para encontrar uma task por id.",
            description = "Essa rota recebe um ID como parâmetro e retorna um objeto do tipo Task que corresponde a esse ID. " +
                    "Caso não exista task com esse ID, a requisição informa que não existe task com esse ID.")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<TaskDTO> findById(@PathVariable UUID id) {
        log.info(">>> TaskControllerImpl - findById: recebendo requisição para encontrar task por id");
        Task task = taskService.findById(id);
        return ResponseEntity.ok().body(convertToDTO(task));
    }

    /**
     * Rota para listar todas as tasks cadastradas
     *
     * @return todas as tasks cadastradas
     */
    @Operation(summary = "Rota para listar todas as tasks cadastradas.")
    @GetMapping
    @Override
    public ResponseEntity<List<TaskDTO>> listAll() {
        log.info(">>> TaskControllerImpl - listAll: recebendo requisição para listar todas as tasks cadastradas");
        List<Task> taskList = taskService.listAll();
        return ResponseEntity.ok().body(taskList.stream().map(DTOConverter::convertToDTO).toList());
    }

    /**
     * Rota para criar uma nova task
     *
     * @param task objeto do tipo Task
     * @return task criada
     */
    @Operation(summary = "Rota para criar uma nova task.",
            description = "Essa rota recebe um objeto do tipo Task no corpo da requisição e, a partir dos dados enviados, cria uma nova Tarefa." +
                    "Caso alguma informação seja enviada equivocadamente, como por exemplo uma data incorreta (35/01/2024), " +
                    "o sistema retorna erros com os status adequados.")
    @PostMapping
    @Override
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody Task task) {
        log.info(">>> TaskControllerImpl - create: recebendo requisição para criar task");
        Task createdTask = taskService.create(task);
        return ResponseEntity.ok().body(convertToDTO(createdTask));
    }

    /**
     * Rota para atualizar uma task
     *
     * @param task objeto do tipo Task
     * @return task atualizada
     */
    @Operation(summary = "Rota para atualizar uma task.",
            description = "Essa rota recebe o ID de uma task na url da rota e um objeto do tipo Task no corpo da requisição, " +
                    "a partir dos dados enviados, atualiza uma task pré-existente. Caso alguma informação seja enviada equivocadamente," +
                    " como por exemplo uma data incorreta (35/01/2024), o sistema retorna erros com os status adequados.")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<TaskDTO> update(@PathVariable UUID id, @Valid @RequestBody @NotNull Task task) {
        log.info(">>> TaskControllerImpl - update: recebendo requisição para atualizar task");
        task.setId(id);
        Task updatedTask = taskService.update(task);
        return ResponseEntity.ok().body(convertToDTO(updatedTask));
    }

    /**
     * Rota para atualizar o status de 'isCompleted' da task
     *
     * @param id id da task
     * @return task atualizada
     */
    @Operation(summary = "Rota para atualizar o status de 'isCompleted' da task.",
            description = "Essa rota é responsável por atualizar o status de 'isCompleted' das tasks, " +
                    "é chamada quando o usuário clica no botão de 'check'. Caso a task não esteja completa, a requisição irá" +
                    "completar a task. Caso ela já esteja completa, a requisição irá remover o status de completa da task.")
    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<TaskDTO> updateIsCompletedStatus(@PathVariable UUID id) {
        log.info(">>> TaskControllerImpl - updateIsCompletedStatus: recebendo requisição para alterar status da task");
        Task updatedTask = taskService.updateIsCompletedStatus(id);
        return ResponseEntity.ok().body(convertToDTO(updatedTask));
    }

    /**
     * Rota para deletar uma task
     *
     * @param id id da task
     * @return task deletada
     */
    @Operation(summary = "Rota para deletar uma task.",
            description = "Essa rota é responsável por deletar uma task a partir do seu ID, que é passado pelo url da rota." +
                    "Caso não exista task com esse ID, a requisição informa que não existe task com esse ID.")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info(">>> TaskControllerImpl - delete: recebendo requisição para deletar task");
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
