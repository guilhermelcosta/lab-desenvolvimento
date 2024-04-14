package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção que é lançada quando o usuário tenta criar uma tarefa com data de conclusão anterior à data atual
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)

public class InvalidDueDateException extends RuntimeException {

    public InvalidDueDateException(String message) {
        super(message);
    }
}
