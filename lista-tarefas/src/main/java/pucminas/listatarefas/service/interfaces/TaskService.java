package pucminas.listatarefas.service.interfaces;

import pucminas.listatarefas.entity.Task;

import java.util.UUID;

/**
 * Especialização da interface de Service de Task
 * Recebe como parâmetro a classe da camada de Service
 */
public interface TaskService extends CrudService<Task> {

    Task updateIsCompletedStatus(UUID id);
}
