package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskDeleteException extends RuntimeException {

    public TaskDeleteException(String message) {
        super(message);
    }
}
