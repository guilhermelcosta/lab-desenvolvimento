package pucminas.listatarefas.service.interfaces;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {

    T findById(UUID id);

    List<T> listAll();

    T create(T t);

    T update(T t);

    void delete(UUID id);
}
