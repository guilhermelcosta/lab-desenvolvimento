package pucminas.todolist.service.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Classes com operações gerais de crud na camada de Service (create, update e delete)
 *
 * @param <T> Classe da camada de Service
 */
public interface CrudService<T> {

    T findById(UUID id);

    List<T> listAll();

    T create(T t);

    T update(T t);

    void delete(UUID id);
}
