package pucminas.todolist.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TaskControllerImplTest {

    @InjectMocks
    private MockMvc mockMvc;

    public void setup() {

    }

//    @Test
//    @DisplayName("findById: success -> return HTTP code 200")
//    void findById_success_returnHttpCode200() throws Exception {
//        // Given
//        mockMvc.perform(MockMvcRequestBuilders.get("https://lab-desenvolvimento.onrender.com/todo-list/task"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // When
//
//        //Then
//    }
}
