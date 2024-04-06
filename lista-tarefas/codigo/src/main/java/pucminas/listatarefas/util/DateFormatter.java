package pucminas.listatarefas.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class DateFormatter {

    /**
     * Formata datas para o padrão dd/MM/yyyy
     *
     * @param date data a ser formatada
     * @return data formatada
     */
    public static @NotNull String formatDate(@NotNull LocalDateTime date) {
        return date.format(ofPattern("dd/MM/yyyy"));
    }
}
