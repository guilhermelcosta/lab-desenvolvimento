package pucminas.listatarefas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FailedDeleteException extends RuntimeException {

    public FailedDeleteException(String message) {
        super(message);
    }
}
