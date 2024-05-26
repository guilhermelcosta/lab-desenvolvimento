package pucminas.todolist.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import pucminas.todolist.TodoListApplication;
import pucminas.todolist.dto.TaskDTO;
import pucminas.todolist.entity.Task;

import java.util.List;

import static org.assertj.core.util.Strings.concat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pucminas.todolist.mocks.TaskMock.getTaskMockWithDaysToComplete;
import static pucminas.todolist.mocks.TaskMock.getTaskMockWithDueDate;
import static pucminas.todolist.util.TaskConstants.TITLE_VER_2;
import static pucminas.todolist.util.constants.Constants.TASK_ENDPOINT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {TodoListApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TaskIntegrationTest {

    @Value(value = "${server.servlet.context-path}")
    private String contextPath;

    private Task taskMock;
    private static TaskDTO taskCreated;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        // Given
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        taskMock = getTaskMockWithDueDate();
    }

    @Test
    @Order(1)
    @DisplayName("create: task created -> should return task created")
    void create_taskCreated_shouldReturnTaskCreated() throws JsonProcessingException {
        // When
        ValidatableResponse validatableResponse = RestAssured.given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(taskMock)
                .post(concat(contextPath, "/", TASK_ENDPOINT))
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(APPLICATION_JSON_VALUE);

        String jsonResponse = validatableResponse
                .extract()
                .body()
                .asString();

        TaskDTO taskCreated = mapper.readValue(jsonResponse, TaskDTO.class);
        TaskIntegrationTest.taskCreated = taskCreated;

        //Then
        assertNotNull(taskCreated);
    }

    @Test
    @Order(2)
    @DisplayName("findAll: tasks found -> should return task list")
    void findAll_tasksFound_shouldReturnTaskList() throws JsonProcessingException {
        // When
        ValidatableResponse validatableResponse = RestAssured.get(concat(contextPath, "/", TASK_ENDPOINT))
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(APPLICATION_JSON_VALUE);

        String jsonResponse = validatableResponse.extract().body().asString();

        List<TaskDTO> tasksFound = mapper.readValue(jsonResponse, new TypeReference<>() {
        });

        //Then
        assertNotNull(tasksFound);
    }

    @Test
    @Order(3)
    @DisplayName("findById: task found -> should return task found")
    void findById_taskFound_shouldReturnTaskFound() throws JsonProcessingException {
        // When
        ValidatableResponse validatableResponse = RestAssured.get(concat(contextPath, "/", TASK_ENDPOINT, "/", taskCreated.id().toString()))
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(APPLICATION_JSON_VALUE);

        String jsonResponse = validatableResponse.extract().body().asString();
        TaskDTO tasksFound = mapper.readValue(jsonResponse, TaskDTO.class);

        //Then
        assertNotNull(tasksFound);
    }

    @Test
    @Order(4)
    @DisplayName("update: task updated -> should return updated task")
    void update_taskUpdated_shouldReturnUpdatedTask() throws JsonProcessingException {
        // Given
        taskMock = getTaskMockWithDaysToComplete();

        // When
        ValidatableResponse validatableResponse = RestAssured.given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(taskMock)
                .put(concat(contextPath, "/", TASK_ENDPOINT, "/", taskCreated.id().toString()))
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(APPLICATION_JSON_VALUE);

        String jsonResponse = validatableResponse
                .extract()
                .body()
                .asString();

        TaskDTO taskUpdated = mapper.readValue(jsonResponse, TaskDTO.class);

        //Then
        assertNotNull(taskUpdated);
        assertEquals(TITLE_VER_2, taskUpdated.title());
    }

    @Test
    @Order(4)
    @DisplayName("updateIsCompleted: task updated -> should return updated task")
    void updateIsCompleted_taskUpdated_shouldReturnUpdatedTask() throws JsonProcessingException {
        // When
        ValidatableResponse validatableResponse = RestAssured.patch(concat(contextPath, "/", TASK_ENDPOINT, "/", taskCreated.id().toString()))
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(APPLICATION_JSON_VALUE);

        String jsonResponse = validatableResponse
                .extract()
                .body()
                .asString();

        TaskDTO taskUpdated = mapper.readValue(jsonResponse, TaskDTO.class);

        //Then
        assertNotNull(taskUpdated);
        assertEquals(!taskCreated.isCompleted(), taskUpdated.isCompleted());
    }

    @Test
    @Order(5)
    @DisplayName("delete: task deleted -> should return no content")
    void delete_taskDeleted_shouldReturnNoContent() throws JsonProcessingException {
        // When e Then
        RestAssured.delete(concat(contextPath, "/", TASK_ENDPOINT, "/", taskCreated.id().toString()))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
