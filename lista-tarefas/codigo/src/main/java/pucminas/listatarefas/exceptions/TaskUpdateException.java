package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Essa exceção é lançada quando há uma falha imprevista durante a atualização de uma nova tarefa
 */
@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class TaskUpdateException extends RuntimeException {

    public TaskUpdateException(String message) {
        super(message);
    }
}
