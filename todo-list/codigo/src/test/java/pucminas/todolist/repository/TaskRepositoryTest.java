package pucminas.todolist.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pucminas.todolist.entity.Task;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static pucminas.todolist.mocks.TaskMock.getTaskMockWithDueDate;
import static pucminas.todolist.util.constants.Constants.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task taskMock;

    @BeforeEach
    public void setUp() {
        taskMock = getTaskMockWithDueDate();
    }

    @Test
    @DisplayName("findById: task found -> return task object")
    void findById_taskFound_returnTaskObject() {
        // Given
        Task taskSaved = taskRepository.save(taskMock);

        // When
        Optional<Task> taskFound = taskRepository.findById(taskSaved.getId());

        //Then
        assertNotNull(taskFound);
        assertEquals(taskSaved.getId(), taskFound.get().getId());
    }

    @Test
    @DisplayName("findById: no task found -> return empty list")
    void findById_noTaskFound_returnEmptyList() {
        // Given e When
        Optional<Task> taskFound = taskRepository.findById(taskMock.getId());

        //Then
        assertTrue(taskFound.isEmpty());
    }


    @Test
    @DisplayName("findAll: tasks found -> return tasks objects")
    void findAll_tasksFound_returnTasksObjects() {
        // Given
        saveTasks(FIVE);

        // When
        List<Task> tasksFound = taskRepository.findAll();

        //Then
        assertNotNull(tasksFound);
        assertEquals(FIVE, tasksFound.size());
    }

    @Test
    @DisplayName("findAll: no tasks found -> return empty list")
    void findAll_notTasksFound_returnEmptyList() {
        // Given
        saveTasks(ZERO);

        // When
        List<Task> tasksFound = taskRepository.findAll();

        //Then
        assertTrue(tasksFound.isEmpty());
    }

    @Test
    @DisplayName("save: task saved -> return task saved")
    void save_taskFound_returnTaskObject() {
        // Given
        Task taskObject = taskMock;

        // When
        Task taskSaved = taskRepository.save(taskObject);

        //Then
        assertNotNull(taskSaved);
        assertNotNull(taskSaved.getId());
    }

    @Test
    @DisplayName("delete: task deleted -> task is no longer in task list")
    void delete_taskDeleted_returnTaskObject() {
        // Given
        saveTasks(ONE);
        Task taskSaved = taskRepository.findAll().get(ZERO);

        // When
        taskRepository.deleteById(taskSaved.getId());

        //Then
        assertEquals(ZERO, taskRepository.findAll().size());
    }

    /**
     * Cria uma quantidade de tarefas, definido a partir da variável 'numberOfTasks'
     *
     * @param numberOfTasks número de tarefas para serem criadas
     */
    private void saveTasks(int numberOfTasks) {
        for (int i = ZERO; i < numberOfTasks; i++)
            taskRepository.save(taskMock);
    }
}
