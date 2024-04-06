package pucminas.listatarefas.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pucminas.listatarefas.entity.Task;
import pucminas.listatarefas.exceptions.TaskCreateException;
import pucminas.listatarefas.exceptions.TaskDeleteException;
import pucminas.listatarefas.exceptions.TaskUpdateException;
import pucminas.listatarefas.repository.TaskRepository;
import pucminas.listatarefas.service.interfaces.TaskService;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static org.springframework.beans.BeanUtils.copyProperties;
import static pucminas.listatarefas.util.Constants.*;
import static pucminas.listatarefas.util.DateFormatter.formatDate;

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
     * @param task objeto do tipo Tarefa
     * @return tarefa criada
     */
    @Override
    public Task create(@NotNull Task task) {
        try {
            log.info(">>> TaskServiceImpl - create: criando tarefa");
            task.setId(null);
            task.setCompleted(false);
            task.setCreateDate(now());
            isDueDateValid(task);
            task.setLastModifiedDate(now());
            task = taskRepository.save(task);
            log.info(format(">>> TaskServiceImpl - create: tarefa criada com sucesso, id: %s", task.getId()));
            return task;
        } catch (Exception e) {
            throw new TaskCreateException(format(MSG_TASK_CREATE_EXCEPTION, e));
        }
    }

    /**
     * Atualiza uma tarefa
     *
     * @param task objeto do tipo Tarefa
     * @return tarefa atualizada
     */
    @Override
    public Task update(@NotNull Task task) {
        try {
            log.info(">>> TaskServiceImpl - update: atualizando tarefa");
            Task updatedTask = findById(task.getId());
            copyProperties(task, updatedTask, IGNORED_PROPERTIES);
            updatedTask.setLastModifiedDate(now());
            updatedTask = taskRepository.save(updatedTask);
            log.info(format(">>> TaskServiceImpl - update: tarefa atualizada com sucesso, id: %s", updatedTask.getId()));
            return updatedTask;
        } catch (Exception e) {
            throw new TaskUpdateException(format(MSG_TASK_UPDATE_EXCEPTION, e));
        }
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
        try {
            Task updatedTask = findById(id);
            boolean isCompleted = !updatedTask.isCompleted();
            updatedTask.setLastModifiedDate(now());
            updatedTask.setCompleted(isCompleted);
            updatedTask.setCompletedDate(isCompleted ? now() : null);
            log.info(format(">>> TaskServiceImpl - updateIsCompletedStatus: status de tarefa alterado com sucesso, id: %s", id));
            return taskRepository.save(updatedTask);
        } catch (Exception e) {
            throw new TaskUpdateException(format(MSG_TASK_UPDATE_EXCEPTION, e));
        }
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
     * Valida se uma data é válida, ou seja, após a data atual
     *
     * @param task objeto do tipo Tarefa
     */
    private void isDueDateValid(@NotNull Task task) throws Exception {
        if (task.getDueDate().isBefore(now()))
            throw new Exception(format("A data de conclusão da tarefa (%s) deve ser posterior à data atual (%s)", formatDate(task.getDueDate()), formatDate(now())));
    }
}
