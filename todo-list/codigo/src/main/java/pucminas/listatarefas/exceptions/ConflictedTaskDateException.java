package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção que é lançada quando o usuário tenta criar uma tarefa que possua data de conclusão e prazo para conclusão.
 * Uma tarefa só pode ter data conclusão OU prazo para conclusão, não ambos.
 */
@ResponseStatus(HttpStatus.CONFLICT)

public class ConflictedTaskDateException extends RuntimeException {

    public ConflictedTaskDateException(String message) {
        super(message);
    }
}
