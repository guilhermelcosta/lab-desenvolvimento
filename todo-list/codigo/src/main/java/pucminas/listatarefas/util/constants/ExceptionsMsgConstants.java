package pucminas.listatarefas.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass

public class ExceptionsMsgConstants {

    public static final String MSG_TASK_DELETE_EXCEPTION = "falha ao deletar tarefa: %s";

    public static final String MSG_TASK_NOT_FOUND_EXCEPTION = "tarefa não encontrada, id: %s";

    public static final String MSG_INVALID_DUE_DATE_EXCEPTION = "A data de conclusão da tarefa (%s) não é posterior à data atual (%s)";

    public static final String MSG_CONFLICTED_TASK_DATE_EXCEPTION = "Uma tarefa só pode ter data conclusão OU prazo para conclusão, não ambos";
}
