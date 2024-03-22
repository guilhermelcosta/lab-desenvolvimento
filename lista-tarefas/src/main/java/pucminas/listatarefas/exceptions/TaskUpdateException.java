package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class TaskUpdateException extends RuntimeException {

    public TaskUpdateException(String message) {
        super(message);
    }
}
