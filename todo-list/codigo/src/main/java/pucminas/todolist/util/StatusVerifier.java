package pucminas.todolist.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pucminas.todolist.entity.Task;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.Period.between;
import static java.util.Objects.nonNull;
import static pucminas.todolist.util.constants.Constants.*;
import static pucminas.todolist.util.constants.DateConstants.dateMap;

@UtilityClass
public class StatusVerifier {

    /**
     * Verifica o status da tarefa para o DTO
     *
     * @param task objeto do tipo Task
     * @return status da tarefa
     */
//    todo: ajustar para tarefas em datas já passadas
    public static @Nullable String verifyStatus(@NotNull Task task) {
        if (nonNull(task.getDueDate())) {
            int daysToComplete = between(now(), task.getDueDate().toLocalDate()).getDays();
            return daysToComplete <= SEVEN
                    ? format(STATUS_MGS01, dateMap.get(now().plusDays(daysToComplete).getDayOfWeek().toString().toLowerCase()))
                    : format(STATUS_MGS02, daysToComplete);
        }
        return null;
    }
}
