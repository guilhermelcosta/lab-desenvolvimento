package pucminas.listatarefas.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucminas.listatarefas.entity.Task;

import java.util.List;
import java.util.UUID;

public interface CrudController<I, O> {

    @GetMapping("/{id}")
    ResponseEntity<O> findById(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<List<O>> listAll();

    @PostMapping
    ResponseEntity<O> create(@RequestBody I i);

    @PutMapping("/{id}")
    ResponseEntity<O> update(@PathVariable UUID id, @RequestBody I i);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
