package pucminas.todolist.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
public class ErrorResponse {

    private final int status;
    private final String message;
    private String stackTrace = "";
    private List<ValidationError> errors;

    /**
     * Adiciona um novo erro de validação à ErrorResponse
     *
     * @param field   campo do erro
     * @param message mensagem de erro
     */
    public void addValidationError(String field, String message) {
        if (isNull(errors))
            this.errors = new ArrayList<>();
        this.errors.add(new ValidationError(field, message));
    }

    /**
     * Converte ErrorResponse para JSON
     *
     * @return ErrorResponse em formato JSON
     */
    public String toJson() {
        return "{\"status\": " + getStatus() + ", " +
                "\"message\": \"" + getMessage() + "\"}";
    }
}
