package pucminas.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Essa exceção é lançada quando há uma falha imprevista durante a exclusão de uma nova tarefa
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TaskDeleteException extends RuntimeException {

    public TaskDeleteException(String message) {
        super(message);
    }
}
