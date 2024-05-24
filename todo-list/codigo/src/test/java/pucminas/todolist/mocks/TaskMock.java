package pucminas.todolist.mocks;

import lombok.experimental.UtilityClass;
import pucminas.todolist.entity.Task;

import static pucminas.todolist.util.TaskConstants.*;
import static pucminas.todolist.util.constants.Constants.FIVE;

@UtilityClass
public class TaskMock {

    /**
     * @return objeto mock de Task com dia de conclusão
     */
    public static Task getTaskMockWithDueDate() {
        return Task.builder()
                .id(ID_MOCK)
                .title(TITLE)
                .description(DESCRIPTION)
                .priority(PRIORITY)
                .tag(TAG)
                .dueDate(DUE_DATE_VALID)
                .completedDate(COMPLETED_DATE)
                .createDate(CREATE_DATE)
                .lastModifiedDate(LAST_MODIFIED_DATE)
                .isCompleted(IS_COMPLETED)
                .build();
    }

        /**
     * @return objeto mock de Task com quantidade dias para conclusão
     */
    public static Task getTaskMockWithDaysToComplete() {
        return Task.builder()
                .id(ID_MOCK)
                .title(TITLE)
                .description(DESCRIPTION)
                .priority(PRIORITY)
                .tag(TAG)
                .daysToComplete(FIVE)
                .completedDate(COMPLETED_DATE)
                .createDate(CREATE_DATE)
                .lastModifiedDate(LAST_MODIFIED_DATE)
                .isCompleted(IS_COMPLETED)
                .build();
    }

    /**
     * Cria um objeto mock de Task com conflito nos atributos de data -> objeto possui data para conclusão E dias para conclusão
     *
     * @return objeto mock de Task com conflito nos atributos de data
     */
    public static Task getTaskMockConflictedDate() {
        return Task.builder()
                .id(ID_MOCK)
                .title(TITLE)
                .description(DESCRIPTION)
                .priority(PRIORITY)
                .tag(TAG)
                .dueDate(DUE_DATE_VALID)
                .daysToComplete(DAYS_TO_COMPLETE)
                .completedDate(COMPLETED_DATE)
                .createDate(CREATE_DATE)
                .lastModifiedDate(LAST_MODIFIED_DATE)
                .isCompleted(IS_COMPLETED)
                .build();
    }

    /**
     * Cria um objeto mock de Task com data de conclusão inválida -> objeto possui data para conclusão inferior à data atual
     *
     * @return objeto mock de Task com data de conclusão inválida
     */
    public static Task getTaskMockInvalidDueDate() {
        return Task.builder()
                .id(ID_MOCK)
                .title(TITLE)
                .description(DESCRIPTION)
                .priority(PRIORITY)
                .tag(TAG)
                .dueDate(DUE_DATE_INVALID)
                .completedDate(COMPLETED_DATE)
                .createDate(CREATE_DATE)
                .lastModifiedDate(LAST_MODIFIED_DATE)
                .isCompleted(IS_COMPLETED)
                .build();
    }
}
