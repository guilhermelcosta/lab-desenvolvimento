package pucminas.listatarefas.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucminas.listatarefas.entity.Task;

import java.util.List;
import java.util.UUID;

public interface CrudController<T> {

    @GetMapping("/{id}")
    ResponseEntity<T> findById(UUID id);

    @GetMapping
    ResponseEntity<List<T>> listAll();

    @PostMapping
    ResponseEntity<Task> create(T t);

    @PutMapping("/{id}")
    ResponseEntity<Task> update(@PathVariable UUID id, T t);

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable UUID id);
}
