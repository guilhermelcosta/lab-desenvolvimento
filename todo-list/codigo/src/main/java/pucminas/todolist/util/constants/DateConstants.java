package pucminas.todolist.util.constants;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class DateConstants {

    public static final String MONDAY = "monday";
    public static final String TUESDAY = "tuesday";
    public static final String WEDNESDAY = "wednesday";
    public static final String THURSDAY = "thursday";
    public static final String FRIDAY = "friday";
    public static final String SATURDAY = "saturday";
    public static final String SUNDAY = "sunday";
    public static final String SEGUNDA_FEIRA = "segunda-feira";
    public static final String TERCA_FEIRA = "terça-feira";
    public static final String QUARTA_FEIRA = "quarta-feira";
    public static final String QUINTA_FEIRA = "quinta-feira";
    public static final String SEXTA_FEIRA = "sexta-feira";
    public static final String SABADO = "sábado";
    public static final String DOMINGO = "domingo";

    public static final Map<String, String> dateMap = Map.of(
            MONDAY, SEGUNDA_FEIRA,
            TUESDAY, TERCA_FEIRA,
            WEDNESDAY, QUARTA_FEIRA,
            THURSDAY, QUINTA_FEIRA,
            FRIDAY, SEXTA_FEIRA,
            SATURDAY, SABADO,
            SUNDAY, DOMINGO
    );
}
