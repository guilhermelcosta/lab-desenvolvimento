package pucminas.listatarefas.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String TASK_ENDPOINT = "task";

    public static final String TB_TASK = "TB_TASK";

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String DESCRIPTION = "description";

    public static final String PRIORITY = "priority";

    public static final String TAG = "tag";

    public static final String DUE_DATE = "due_date";

    public static final String COMPLETED_DATE = "completed_date";

    public static final String CREATE_DATE = "create_date";

    public static final String LAST_MODIFIED_DATE = "last_modified_date";

    public static final String IS_COMPLETED = "is_completed";

    public static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

    public static final String MSG_NAME_ERROR = "o nome da tarefa não deve conter caracteres especiais";

    public static final String MSG_TASK_UPDATE_EXCEPTION = "falha ao atualizar tarefa: %s";

    public static final String MSG_TASK_DELETE_EXCEPTION = "falha ao deletar tarefa: %s";

    public static final String MSG_TASK_CREATE_EXCEPTION = "falha ao criar tarefa: %s";

    public static final String MSG_TASK_NOT_FOUND_EXCEPTION = "tarefa não encontrada, id: %s";
}
