package pucminas.listatarefas.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class DateFormatter {

    public static @NotNull String formatDate(LocalDateTime date) {
        return date.format(ofPattern("dd/mm/yyyy"));
    }
}
