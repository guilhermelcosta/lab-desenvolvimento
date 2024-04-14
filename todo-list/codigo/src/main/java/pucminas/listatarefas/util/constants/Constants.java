package pucminas.listatarefas.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final Integer SEVEN = 7;

    public static final String TASK_ENDPOINT = "task";

    public static final String TB_TASK = "TB_TASK";

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String DESCRIPTION = "description";

    public static final String PRIORITY = "priority";

    public static final String TAG = "tag";

    public static final String DUE_DATE = "due_date";

    public static final String DAYS_TO_COMPLETE = "days_to_complete";

    public static final String COMPLETED_DATE_JSON = "completed_date";

    public static final String COMPLETED_DATE = "completedDate";

    public static final String CREATE_DATE_JSON = "create_date";

    public static final String CREATE_DATE = "createDate";

    public static final String LAST_MODIFIED_DATE_JSON = "last_modified_date";

    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";

    public static final String IS_COMPLETED_JSON = "is_completed";

    public static final String IS_COMPLETED = "isCompleted";

    public static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

    public static final String[] IGNORED_PROPERTIES = {ID, COMPLETED_DATE, CREATE_DATE, LAST_MODIFIED_DATE, IS_COMPLETED};

    public static final String MSG_NAME_ERROR = "o nome da tarefa não deve conter caracteres especiais";

    public static final String TASK_SERVICE = "Task Service";

    public static final String TASK_CONTROLLER = "Task Controller";

    public static final String LOGGING_INTERCEPTOR = "Logging Interceptor";

    public static final String EXCEPTION_INTERCEPTOR = "Exception Interceptor";

    public static final String NEXT_MSG = "Next %s";

    public static final String IN_MSG = "In %s days";
}
