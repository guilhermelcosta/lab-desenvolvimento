package pucminas.todolist.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pucminas.todolist.entity.Task;
import pucminas.todolist.exceptions.ConflictedTaskDateException;
import pucminas.todolist.exceptions.InvalidDueDateException;
import pucminas.todolist.exceptions.TaskDeleteException;
import pucminas.todolist.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static pucminas.todolist.mocks.TaskMock.*;
import static pucminas.todolist.util.TaskConstants.ID_MOCK;
import static pucminas.todolist.util.constants.Constants.FIVE;
import static pucminas.todolist.util.constants.Constants.SEVEN;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
public class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task taskMockWithDueDate;
    private Task taskMockWithDaysToComplete;
    private Task taskMockDateConflict;
    private Task taskMockInvalidDueDate;

    @BeforeEach
    public void setUp() {
        taskMockWithDueDate = getTaskMockWithDueDate();
        taskMockWithDaysToComplete = getTaskMockWithDaysToComplete();
        taskMockDateConflict = getTaskMockConflictedDate();
        taskMockInvalidDueDate = getTaskMockInvalidDueDate();
    }

    @Test
    @DisplayName("findById: id found -> return task object")
    void findById_idFound_returnTaskObject() {
        // Given
        UUID ID = ID_MOCK;

        // When
        when(taskRepository.findById(ID)).thenReturn(ofNullable(taskMockWithDueDate));
        Task taskFound = taskService.findById(ID);

        //Then
        assertEquals(taskMockWithDueDate, taskFound);
    }

    @Test
    @Order(2)
    @DisplayName("findById: id not found -> throw EntityNotFoundException")
    void findById_idNotFound_throwEntityNotFoundException() {
        // Given
        UUID ID = ID_MOCK;

        // When
        when(taskRepository.findById(ID)).thenThrow(EntityNotFoundException.class);

        //Then
        assertThrows(EntityNotFoundException.class, () -> taskService.findById(ID));
    }

    @Test
    @DisplayName("findAll: list not empty -> return Task list")
    void listAll_listNotEmpty_returnTaskFind() {
        // Given
        List<Task> taskMockList = asList(taskMockWithDueDate, taskMockWithDueDate);

        // When
        when(taskRepository.findAll()).thenReturn(taskMockList);
        List<Task> tasksFound = taskService.findAll();

        //Then
        assertEquals(taskMockList, tasksFound);
    }

    @Test
    @DisplayName("findAll: list empty -> return empty Task list")
    void listAll_listEmpty_returnEmptyTaskList() {
        // Given
        List<Task> emptyTaskMockList = emptyList();

        // When
        when(taskRepository.findAll()).thenReturn(emptyTaskMockList);
        List<Task> tasksFound = taskService.findAll();

        //Then
        assertEquals(emptyTaskMockList, tasksFound);
    }

    @Test
    @DisplayName("create: success (due date) -> return created Task")
    void create_successDueDate_returnCreatedTask() {
        // Given
        Task taskPayload = taskMockWithDueDate;

        // When
        when(taskRepository.save(taskPayload)).thenReturn(taskPayload);
        Task taskCreated = taskService.create(taskPayload);

        //Then
        assertEquals(taskPayload, taskCreated);
    }

    @Test
    @DisplayName("create: success (days to complete) -> return created Task")
    void create_successDaysToComplete_returnCreatedTask() {
        // Given
        Task taskPayload = taskMockWithDaysToComplete;

        // When
        when(taskRepository.save(taskPayload)).thenReturn(taskPayload);
        Task taskCreated = taskService.create(taskPayload);

        //Then
        assertEquals(taskPayload, taskCreated);
    }

    @Test
    @DisplayName("create: conflicted task date -> throw ConflictedTaskDateException")
    void create_conflictedTaskDate_throwConflictedTaskDateException() {
        // Given
        Task taskPayload = taskMockDateConflict;

        //When e Then
        assertThrows(ConflictedTaskDateException.class, () -> taskService.create(taskPayload));
    }

    @Test
    @DisplayName("create: invalid due date -> throw InvalidDueDateException")
    void create_invalidDueDate_throwInvalidDueDateException() {
        // Given
        Task taskPayload = taskMockInvalidDueDate;

        //When e Then
        assertThrows(InvalidDueDateException.class, () -> taskService.create(taskPayload));
    }

    @Test
    @DisplayName("update: success (due date) -> return updated Task")
    void update_successDueDate_returnUpdatedTask() {
        // Given
        Task taskPayload = taskMockWithDueDate;

        // When
        when(taskRepository.findById(taskPayload.getId())).thenReturn(ofNullable(taskMockWithDueDate));
        when(taskRepository.save(taskPayload)).thenReturn(taskPayload);
        Task taskUpdated = taskService.update(taskPayload);

        //Then
        assertEquals(taskPayload, taskUpdated);
    }

    @Test
    @DisplayName("update: success (days to complete) -> return updated Task")
    void update_successDaysToComplete_returnUpdatedTask() {
        // Given
        Task taskPayload = taskMockWithDaysToComplete;

        // When
        when(taskRepository.findById(taskPayload.getId())).thenReturn(ofNullable(taskMockWithDaysToComplete));
        when(taskRepository.save(taskPayload)).thenReturn(taskPayload);
        Task taskUpdated = taskService.update(taskPayload);

        //Then
        assertEquals(taskPayload, taskUpdated);
    }

    @Test
    @DisplayName("update: ignore defined properties -> return updated task without updating defined properties")
    void update_ignoreDefinedProperties_returnUpdatedTaskWithoutUpdatingDefinedProperties() {
        // Given
        Task taskPayloadWithModifiedIgnoredProperties = taskMockWithDueDate;
        taskPayloadWithModifiedIgnoredProperties.setId(randomUUID());
        taskPayloadWithModifiedIgnoredProperties.setCompletedDate(now().plusDays(SEVEN));
        taskPayloadWithModifiedIgnoredProperties.setCreateDate(now().minusDays(SEVEN));
        taskPayloadWithModifiedIgnoredProperties.setLastModifiedDate(now().plusDays(FIVE));
        taskPayloadWithModifiedIgnoredProperties.setCompleted(!taskPayloadWithModifiedIgnoredProperties.isCompleted());

        // When
        when(taskRepository.findById(taskPayloadWithModifiedIgnoredProperties.getId()))
                .thenReturn(ofNullable(taskMockWithDueDate));
        when(taskRepository.save(taskPayloadWithModifiedIgnoredProperties))
                .thenReturn(taskPayloadWithModifiedIgnoredProperties);
        Task taskUpdated = taskService.update(taskPayloadWithModifiedIgnoredProperties);

        //Then
        assertEquals(taskPayloadWithModifiedIgnoredProperties, taskUpdated);
    }

    @Test
    @DisplayName("update: conflicted task date -> throw ConflictedTaskDateException")
    void update_conflictedTaskDate_throwConflictedTaskDateException() {
        // Given
        Task taskPayload = taskMockDateConflict;

        //When e Then
        assertThrows(ConflictedTaskDateException.class, () -> taskService.update(taskPayload));
    }

    @Test
    @DisplayName("update: invalid due date -> throw InvalidDueDateException")
    void update_invalidDueDate_throwInvalidDueDateException() {
        // Given
        Task taskPayload = taskMockInvalidDueDate;

        //When e Then
        assertThrows(InvalidDueDateException.class, () -> taskService.update(taskPayload));
    }

    @Test
    @DisplayName("updateIsCompletedStatus: status updates (true to false) -> return updated task")
    void updateIsCompletedStatus_statusUpdatedTrueToFalse_returnUpdatedTask() {
        // Given
        UUID ID = ID_MOCK;
        boolean currentStatus = taskMockWithDueDate.isCompleted();

        //When
        when(taskRepository.findById(ID)).thenReturn(ofNullable(taskMockWithDueDate));
        when(taskRepository.save(taskMockWithDueDate)).thenReturn(taskMockWithDueDate);
        Task taskUpdated = taskService.updateIsCompletedStatus(ID);

        // Then
        assertEquals(!currentStatus, taskUpdated.isCompleted());
    }

    @Test
    @DisplayName("updateIsCompletedStatus: status updates (false to true) -> return updated task")
    void updateIsCompletedStatus_statusUpdatedFalseToTrue_returnUpdatedTask() {
        // Given
        UUID ID = ID_MOCK;
        Task taskMock = taskMockWithDueDate;
        taskMock.setCompleted(!taskMock.isCompleted());
        boolean currentStatus = taskMockWithDueDate.isCompleted();

        //When
        when(taskRepository.findById(ID)).thenReturn(ofNullable(taskMockWithDueDate));
        when(taskRepository.save(taskMockWithDueDate)).thenReturn(taskMockWithDueDate);
        Task taskUpdated = taskService.updateIsCompletedStatus(ID);

        // Then
        assertEquals(!currentStatus, taskUpdated.isCompleted());
    }

    @Test
    @DisplayName("delete: task deleted -> does not throw TaskDeleteException")
    void delete_taskDeleted_doesNotThrowTaskDeleteException() {
        // Given
        UUID ID = ID_MOCK;

        //When
        when(taskRepository.findById(ID)).thenReturn(ofNullable(taskMockWithDueDate));

        // Then
        assertDoesNotThrow(() -> taskService.delete(ID));
    }

    @Test
    @DisplayName("delete: error to delete task -> throw TaskDeleteException")
    void delete_errorToDeleteTask_throwTaskDeleteException() {
        // Given
        UUID ID = ID_MOCK;

        //When
        when(taskRepository.findById(ID)).thenReturn(ofNullable(taskMockWithDueDate));
        doThrow(TaskDeleteException.class).when(taskRepository).deleteById(ID);

        // Then
        assertThrows(TaskDeleteException.class, () -> taskService.delete(ID));
    }

}
