package pucminas.listatarefas.controller;

import org.springframework.web.bind.annotation.*;
import pucminas.listatarefas.entity.Task;

import java.util.UUID;

import static pucminas.listatarefas.util.Constants.TASK_ENDPOINT;

@RestController
@RequestMapping(TASK_ENDPOINT)
public class TaskController {

    @GetMapping()
    public String listAll() {
        return "Listando todas as tarefas...";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable UUID id) {
        return "Obtendo tarefa...";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable UUID id, Task task) {
        return "Atualizando tarefa...";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return "Deletando tarefa...";
    }
}
