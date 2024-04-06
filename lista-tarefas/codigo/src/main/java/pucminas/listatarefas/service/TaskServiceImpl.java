package pucminas.listatarefas.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pucminas.listatarefas.entity.Task;
import pucminas.listatarefas.exceptions.ConflictedTaskDateException;
import pucminas.listatarefas.exceptions.InvalidDueDateException;
import pucminas.listatarefas.exceptions.TaskDeleteException;
import pucminas.listatarefas.repository.TaskRepository;
import pucminas.listatarefas.service.interfaces.TaskService;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static org.springframework.beans.BeanUtils.copyProperties;
import static pucminas.listatarefas.util.DateFormatter.formatDate;
import static pucminas.listatarefas.util.constants.Constants.IGNORED_PROPERTIES;
import static pucminas.listatarefas.util.constants.Constants.TASK_SERVICE;
import static pucminas.listatarefas.util.constants.ExceptionsMsgConstants.*;

@Slf4j(topic = TASK_SERVICE)
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    /**
     * Encontra uma tarefa a partir do seu if
     *
     * @param id id da tarefa
     * @return tarefa encontrada
     */
    @Override
    public Task findById(UUID id) {
        log.info(format(">>> TaskServiceImpl - findById: encontrando tarefa por id: %s", id));
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(MSG_TASK_NOT_FOUND_EXCEPTION, id)));
    }

    /**
     * Lista todas as tarefas cadastradas
     *
     * @return todas as tarefas cadastradas
     */
    @Override
    public List<Task> listAll() {
        log.info(">>> TaskServiceImpl - listAll: listando todas as tarefas");
        return taskRepository.findAll();
    }

    /**
     * Cria uma nova tarefa
     *
     * @param task objeto do tipo Task
     * @return tarefa criada
     */
    @Override
    public Task create(@NotNull Task task) {
        log.info(">>> TaskServiceImpl - create: criando tarefa");
        validateTaskFields(task);
        task.setId(null);
        task.setCompleted(false);
        task.setCreateDate(now());
        task.setLastModifiedDate(now());
        task = taskRepository.save(task);
        log.info(format(">>> TaskServiceImpl - create: tarefa criada com sucesso, id: %s", task.getId()));
        return task;
    }

    /**
     * Atualiza uma tarefa
     *
     * @param task objeto do tipo Task
     * @return tarefa atualizada
     */
    @Override
    public Task update(@NotNull Task task) {
        log.info(">>> TaskServiceImpl - update: atualizando tarefa");
        validateTaskFields(task);
        Task updatedTask = findById(task.getId());
        copyProperties(task, updatedTask, IGNORED_PROPERTIES);
        updatedTask.setLastModifiedDate(now());
        updatedTask = taskRepository.save(updatedTask);
        log.info(format(">>> TaskServiceImpl - update: tarefa atualizada com sucesso, id: %s", updatedTask.getId()));
        return updatedTask;
    }

    /**
     * Muda o status 'isCompleted' da tarefa, se ela estiver com o atributo is_completed = true,
     * troca para false, e vice-versa
     *
     * @param id id da tarefa
     * @return tarefa atualizada
     */
    @Override
    public Task updateIsCompletedStatus(UUID id) {
        log.info(">>> TaskServiceImpl - updateIsCompletedStatus: alterando status da tarefa");
        Task updatedTask = findById(id);
        boolean isCompleted = !updatedTask.isCompleted();
        updatedTask.setLastModifiedDate(now());
        updatedTask.setCompleted(isCompleted);
        updatedTask.setCompletedDate(isCompleted ? now() : null);
        log.info(format(">>> TaskServiceImpl - updateIsCompletedStatus: status de tarefa alterado com sucesso, id: %s", id));
        return taskRepository.save(updatedTask);
    }

    /**
     * Deleta uma tarefa
     *
     * @param id id da tarefa
     */
    @Override
    public void delete(UUID id) {
        log.info(format(">>> TaskServiceImpl - delete: deletando tarefa, id: %s", id));
        findById(id);
        try {
            taskRepository.deleteById(id);
            log.info(format(">>> TaskServiceImpl - delete: tarefa deletada com sucesso, id: %s", id));
        } catch (Exception e) {
            throw new TaskDeleteException(format(MSG_TASK_DELETE_EXCEPTION, e));
        }
    }

    /**
     * Faz validações de campos da tarefa:
     * - Data de conclusão inválida (data de conclusão > data atual)
     * - Datas conflitantes: tarefa possui data de conclusão E prazo para conclusão, só pode haver um dos dois
     *
     * @param task objeto do tipo Task
     */
    // todo: fazer interface de validadores
    private void validateTaskFields(Task task) {
        isDueDateValid(task);
        hasConflictedDates(task);
        calculateDueDate(task);
    }

    /**
     * Valida se uma data é válida, ou seja, após a data atual
     *
     * @param task objeto do tipo Task
     */
    private void isDueDateValid(@NotNull Task task) {
        if (nonNull(task.getDueDate()) && task.getDueDate().isBefore(now()))
            throw new InvalidDueDateException(format(MSG_INVALID_DUE_DATE_EXCEPTION, formatDate(task.getDueDate()), formatDate(now())));
    }

    /**
     * Valida se a tarefa possui data de conclusão E prazo para conclusão, só pode haver um dos dois
     *
     * @param task objeto do tipo Task
     */
    private void hasConflictedDates(@NotNull Task task) {
        if (nonNull(task.getDueDate()) && nonNull(task.getDaysToComplete()))
            throw new ConflictedTaskDateException(MSG_CONFLICTED_TASK_DATE_EXCEPTION);
    }

    /**
     * Calcula a data de conclusão de uma tarefa a partir do seu prazo
     *
     * @param task objeto do tipo Task
     */
    private void calculateDueDate(@NotNull Task task) {
        if (nonNull(task.getDaysToComplete()))
            task.setDueDate(now().plusDays(task.getDaysToComplete()));
    }
}
