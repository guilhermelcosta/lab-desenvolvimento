package pucminas.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pucminas.todolist.entity.Task;
import pucminas.todolist.service.interfaces.TaskService;

import java.util.List;

import static com.jayway.jsonpath.internal.Utils.concat;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pucminas.todolist.mocks.TaskMock.getTaskMockWithDueDate;
import static pucminas.todolist.util.TaskConstants.ID_MOCK;
import static pucminas.todolist.util.constants.Constants.TASK_ENDPOINT;
import static pucminas.todolist.util.constants.Constants.ZERO;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private Task taskMock;

    @BeforeEach
    public void setUp() {
        taskMock = getTaskMockWithDueDate();
    }

    @Test
    @DisplayName("findById: task found -> return task object")
    void findById_taskFound_returnTaskObject() throws Exception {
        // Given
        given(taskService.findById(ID_MOCK)).willReturn(taskMock);

        // When
        ResultActions apiResponse = mockMvc.perform(get(concat("/", TASK_ENDPOINT, "/", ID_MOCK.toString())));

        //Then
        apiResponse.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("findAll: list all tasks -> return task list")
    void findAll_listAllTasks_returnTaskList() throws Exception {
        // Given
        List<Task> tasks = asList(taskMock, taskMock);
        given(taskService.findAll()).willReturn(tasks);

        // When
        ResultActions apiResponse = mockMvc.perform(get(concat("/", TASK_ENDPOINT)));

        //Then
        apiResponse.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("post: task is create -> return created task")
    void post_taskIsCreated_returnCreatedTask() throws Exception {
        // Given
        given(taskService.create(any(Task.class)))
                .willAnswer((invocation) -> invocation.getArgument(ZERO));

        // When
        ResultActions apiResponse = mockMvc.perform(post(concat("/", TASK_ENDPOINT))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskMock)));

        //Then
        apiResponse.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("update: task updated -> return task updated")
    void update_taskUpdated_returnTaskUpdated() throws Exception {
        // Given
        given(taskService.update(any(Task.class)))
                .willAnswer((invocation) -> invocation.getArgument(ZERO));

        // When
        ResultActions apiResponse = mockMvc.perform(put(concat("/", TASK_ENDPOINT, "/", ID_MOCK.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskMock)));

        //Then
        apiResponse.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("patch: task updated -> return task updated")
    void patch_taskUpdated_returnTaskUpdated() throws Exception {
        // Given
        given(taskService.updateIsCompletedStatus(ID_MOCK))
                .willReturn(taskMock);

        // When
        ResultActions apiResponse = mockMvc.perform(patch(concat("/", TASK_ENDPOINT, "/{id}"), ID_MOCK.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskMock)));

        // Then
        apiResponse.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete: task deleted -> task must be deleted")
    void delete_taskDeleted_taskMustBeDeleted() throws Exception {
        // Given
        willDoNothing().given(taskService).delete(ID_MOCK);

        // When
        ResultActions apiResponse = mockMvc.perform(delete(concat("/", TASK_ENDPOINT, "/{id}"), ID_MOCK.toString()));
        //Then
        apiResponse
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
