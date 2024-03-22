package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskCreateException extends RuntimeException {

    public TaskCreateException(String message) {
        super(message);
    }
}
