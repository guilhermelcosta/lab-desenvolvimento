package pucminas.listatarefas.service.interfaces;

import pucminas.listatarefas.entity.Task;

import java.util.UUID;

public interface TaskService extends CrudService<Task> {

    Task changeIsComplete(UUID id);
}
