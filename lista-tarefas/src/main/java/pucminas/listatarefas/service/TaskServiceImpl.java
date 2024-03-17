package pucminas.listatarefas.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pucminas.listatarefas.entity.Task;
import pucminas.listatarefas.exceptions.FailedDeleteException;
import pucminas.listatarefas.repository.TaskRepository;
import pucminas.listatarefas.service.interfaces.TaskService;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
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
        log.info(format("TaskServiceImpl - findById: encontrando tarefa por id: %s", id));
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
    }

    /**
     * Lista todas as tarefas cadastradas
     *
     * @return todas as tarefas cadastradas
     */
    @Override
    public List<Task> listAll() {
        log.info("TaskServiceImpl - listAll: listando todas as tarefas");
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
        log.info(format("TaskServiceImpl - create: criando tarefa, id: %s", task.getId()));
        task.setId(null);
        task.setCompleted(false);
        task.setCreateDate(now());
        task.setLastModifiedDate(now());
        task = taskRepository.save(task);
        log.info(format("TaskServiceImpl - create: tarefa criada com sucesso, id: %s", task.getId()));
        return task;
    }

    /**
     * Atualiza uma tarefa
     *
     * @param task objeto do tipo Tarefa
     * @return tarefa atualizada
     */
    @Override
    public Task update(@NotNull Task task) {
        log.info(format("TaskServiceImpl - update: atualizando tarefa, id: %s", task.getId()));
        Task updatedTask = findById(task.getId());
        copyProperties(task, updatedTask);
        updatedTask.setLastModifiedDate(now());
        updatedTask = taskRepository.save(updatedTask);
        log.info(format("TaskServiceImpl - update: tarefa atualizado, id: %s", updatedTask.getId()));
        return updatedTask;
    }

    /**
     * Deleta uma tarefa
     *
     * @param id id da tarefa
     */
    @Override
    public void delete(UUID id) {
        log.info(format("TaskServiceImpl - delete: deletando tarefa, id: %s", id));
        try {
            taskRepository.deleteById(id);
            log.info(format("TaskServiceImpl - delete: tarefa deletada com sucesso, id: %s", id));
        } catch (Exception e) {
            throw new FailedDeleteException(format("Falha ao deletar entidade de id: %s", id));
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
        log.info(format("TaskServiceImpl - updateIsCompletedStatus: alterando status da tarefa, id: %s", id));
        Task updatedTask = findById(id);
        updatedTask.setLastModifiedDate(now());
        updatedTask.setCompleted(!updatedTask.isCompleted());
        if (!updatedTask.isCompleted())
            updatedTask.setCompletedDate(null);
        else
            updatedTask.setCompletedDate(now());
        log.info(format("TaskServiceImpl - updateIsCompletedStatus: Status de tarefa alterado com sucesso, id: %s", id));
        return taskRepository.save(updatedTask);
    }
}
