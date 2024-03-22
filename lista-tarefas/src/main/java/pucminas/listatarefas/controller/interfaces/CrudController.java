package pucminas.listatarefas.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucminas.listatarefas.entity.Task;

import java.util.List;
import java.util.UUID;

public interface CrudController<T> {

    @GetMapping("/{id}")
    ResponseEntity<T> findById(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<List<T>> listAll();

    @PostMapping
    ResponseEntity<T> create(@RequestBody T t);

    @PutMapping("/{id}")
    ResponseEntity<T> update(@PathVariable UUID id, @RequestBody T t);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
