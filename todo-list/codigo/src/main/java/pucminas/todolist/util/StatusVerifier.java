package pucminas.todolist.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import pucminas.todolist.entity.Task;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.Period.between;
import static pucminas.todolist.util.constants.Constants.*;

@UtilityClass
public class StatusVerifier {

    /**
     * Verifica o status da tarefa para o DTO
     *
     * @param task objeto do tipo Task
     * @return status da tarefa
     */
//    todo: ajustar para tarefas em datas já passadas
    public static String verifyStatus(@NotNull Task task) {
        int daysToComplete = between(now(), task.getDueDate().toLocalDate()).getDays();
        return daysToComplete <= SEVEN
                ? format(NEXT_MSG, now().plusDays(daysToComplete).getDayOfWeek()).toLowerCase()
                : format(IN_MSG, daysToComplete);
    }
}
