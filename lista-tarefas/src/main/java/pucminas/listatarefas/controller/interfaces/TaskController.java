package pucminas.listatarefas.controller.interfaces;

import pucminas.listatarefas.entity.Task;

import java.util.UUID;

public interface TaskController extends CrudController<Task> {

        Task changeIsComplete(UUID id);
}
