package pucminas.listatarefas.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import pucminas.listatarefas.exceptions.ConflictedTaskDateException;
import pucminas.listatarefas.exceptions.ErrorResponse;
import pucminas.listatarefas.exceptions.InvalidDueDateException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;
import static pucminas.listatarefas.util.constants.Constants.EXCEPTION_INTERCEPTOR;

@Component
@RestControllerAdvice
@Slf4j(topic = EXCEPTION_INTERCEPTOR)
public class ExceptionInterceptor extends DefaultHandlerExceptionResolver {

    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    /**
     * Captura exceções do tipo Exception (método geral para capturar qualquer exceção não tratada)
     *
     * @param e       exceção do tipo Exception
     * @param request requisição
     * @return tratamento da exceção (log e resposta requisição)
     */
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> capturarException(@NotNull Exception e, WebRequest request) {
        final String errorMsg = e.getMessage();
        log.error(format("[ERRO] Exception: erro desconhecido ocorrido: %s", errorMsg));
        return createErrorResponse(e, errorMsg, INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Captura exceções do tipo HttpMessageNotReadableException
     *
     * @param e       exceção do tipo HttpMessageNotReadableException
     * @param request requisição
     * @return tratamento da exceção (log e resposta requisição)
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> captureInvalidDueDateException(@NotNull HttpMessageNotReadableException e, WebRequest request) {
        final String errorMsg = e.getMessage();
        log.error(format("[ERRO] HttpMessageNotReadableException: erro no JSON enviado: %s", errorMsg));
        return createErrorResponse(e, errorMsg, BAD_REQUEST, request);
    }

    /**
     * Captura exceções do tipo InvalidDueDateException
     *
     * @param e       exceção do tipo InvalidDueDateException
     * @param request requisição
     * @return tratamento da exceção (log e resposta requisição)
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidDueDateException.class)
    public ResponseEntity<Object> captureInvalidDueDateException(@NotNull InvalidDueDateException e, WebRequest request) {
        final String errorMsg = e.getMessage();
        log.error(format("[ERRO] InvalidDueDateException: data inválida: %s", errorMsg));
        return createErrorResponse(e, errorMsg, BAD_REQUEST, request);
    }

    /**
     * Captura exceções do tipo ConflictedTaskDateException
     *
     * @param e       exceção do tipo ConflictedTaskDateException
     * @param request requisição
     * @return tratamento da exceção (log e resposta requisição)
     */
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ConflictedTaskDateException.class)
    public ResponseEntity<Object> captureConflictedTaskDateException(@NotNull ConflictedTaskDateException e, WebRequest request) {
        final String errorMsg = e.getMessage();
        log.error(format("[ERRO] ConflictedTaskDateException: tarefa com datas conflitantes: %s", errorMsg));
        return createErrorResponse(e, errorMsg, CONFLICT, request);
    }

    /**
     * Constrói message de erro
     *
     * @param e          exceção
     * @param message    mensagem de erro
     * @param httpStatus status http
     * @param request    requisição
     * @return mensagem de erro da requisição
     */
    private @NotNull ResponseEntity<Object> createErrorResponse(Exception e, String message, @NotNull HttpStatus httpStatus, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (printStackTrace)
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(e));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
