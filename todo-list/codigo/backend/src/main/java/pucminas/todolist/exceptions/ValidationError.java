package pucminas.todolist.exceptions;

public record ValidationError(String field, String message) {
}
