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

    @Override
    public Task findById(UUID id) {
        log.info(format("TaskServiceImpl - findById: encontrando tarefa por id: %s", id));
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
    }

    @Override
    public List<Task> listAll() {
        log.info("TaskServiceImpl - listAll: listando todas as tarefas");
        return taskRepository.findAll();
    }

    @Override
    public Task create(@NotNull Task task) {
        log.info(format("TaskServiceImpl - create: criando tarefa: %s", task.getId()));
        task.setId(null);
        task.setCompleted(false);
        task.setCreateDate(now());
        task.setLastModifiedDate(now());
        task = taskRepository.save(task);
        return task;
    }

    @Override
    public Task update(@NotNull Task task) {
        log.info(format("TaskServiceImpl - update: atualizando tarefa: %s", task.getId()));
        Task updatedTask = findById(task.getId());
        copyProperties(task, updatedTask);
        task.setLastModifiedDate(now());
        task = taskRepository.save(task);
        return task;
    }

    @Override
    public void delete(UUID id) {
        log.info(format("TaskServiceImpl - delete: deletando tarefa: %s", id));
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new FailedDeleteException(format("Falha ao deletar entidade de id: %s", id));
        }
    }
}
