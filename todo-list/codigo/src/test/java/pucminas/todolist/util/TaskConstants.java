package pucminas.todolist.util;

import lombok.experimental.UtilityClass;
import pucminas.todolist.enums.Priority;
import pucminas.todolist.enums.Tag;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static pucminas.todolist.enums.Priority.LOW;
import static pucminas.todolist.enums.Tag.DESPESAS_CASA;
import static pucminas.todolist.util.constants.Constants.FIVE;

@UtilityClass
public class TaskConstants {

    public static UUID ID_MOCK = randomUUID();

    public static String TITLE = "Title";

    public static String TITLE_VER_2 = "Title - ver.2";

    public static String DESCRIPTION = "Description";

    public static Priority PRIORITY = LOW;

    public static Tag TAG = DESPESAS_CASA;

    public static LocalDateTime DUE_DATE_VALID = now().plusDays(FIVE);

    public static LocalDateTime DUE_DATE_INVALID = now();

    public static int DAYS_TO_COMPLETE = FIVE;

    public static LocalDateTime COMPLETED_DATE = now();

    public static LocalDateTime CREATE_DATE = now();

    public static LocalDateTime LAST_MODIFIED_DATE = now();

}
