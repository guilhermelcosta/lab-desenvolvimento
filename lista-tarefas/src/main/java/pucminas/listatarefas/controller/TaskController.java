package pucminas.listatarefas.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
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
    public String update(@PathVariable UUID id) {
        return "Atualizando tarefa...";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return "Deletando tarefa...";
    }
}
