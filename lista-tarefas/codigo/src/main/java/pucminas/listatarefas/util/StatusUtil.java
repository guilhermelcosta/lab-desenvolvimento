package pucminas.listatarefas.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import pucminas.listatarefas.entity.Task;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.Period.between;
import static pucminas.listatarefas.util.constants.Constants.SEVEN;

@UtilityClass
public class StatusUtil {

    /**
     * Calcula o status da tarefa para o DTO
     *
     * @param task objeto do tipo Task
     * @return status da tarefa
     */
    public static String calculateStatus(@NotNull Task task) {
        int daysToComplete = between(now(), task.getDueDate().toLocalDate()).getDays();
        if (daysToComplete == SEVEN)
            return format("Next %s", now().plusDays(SEVEN).getDayOfWeek()).toLowerCase();
        else
            return format("In %s days", daysToComplete);
    }
}
