package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Essa exceção é lançada quando há uma falha imprevista durante a criação de uma nova tarefa
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TaskCreateException extends RuntimeException {

    public TaskCreateException(String message) {
        super(message);
    }
}
