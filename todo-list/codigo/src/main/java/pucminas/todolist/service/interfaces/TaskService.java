package pucminas.todolist.service.interfaces;

import pucminas.todolist.entity.Task;

import java.util.UUID;

/**
 * Especialização da interface de Service de Task
 * Recebe como parâmetro a classe da camada de Service
 */
public interface TaskService extends CrudService<Task> {

    Task updateIsCompletedStatus(UUID id);
}
