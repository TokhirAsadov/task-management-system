package uz.oson.taskmanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uz.oson.taskmanagementsystem.controller.TaskController;
import uz.oson.taskmanagementsystem.entity.TaskStatus;
import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;
import uz.oson.taskmanagementsystem.payload.TaskUpdater;
import uz.oson.taskmanagementsystem.service.TaskService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Slf4j
public class TestingWithMock {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskCreator taskCreator;
    private TaskUpdater taskUpdater;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        taskCreator = new TaskCreator("Sample Task", "Sample Description",null, TaskStatus.OPEN);
        taskUpdater = new TaskUpdater("Updated Task", "Updated Description",null, TaskStatus.OPEN);
        taskResponse = new TaskResponse(UUID.randomUUID(), "Sample Task", "Sample Description",null, TaskStatus.OPEN);
    }


    /***================      /tasks POST API  << MOCKITO >> testing     *============**/
    @Test
    public void testCreateTaskMock() throws Exception {
        when(taskService.createTask(any(TaskCreator.class))).thenReturn(taskResponse);

        log.info("testCreateTaskMock is starting");

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskCreator)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskResponse.id().toString()))
                .andExpect(jsonPath("$.title").value(taskResponse.title()));

        log.info("testCreateTaskMock is over successfully");
    }


}
