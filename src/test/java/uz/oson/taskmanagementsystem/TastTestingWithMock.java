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

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Slf4j
public class TastTestingWithMock {

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


    /***================      /tasks/{id} PUT API  << MOCKITO >> testing     *============**/
    @Test
    public void testUpdateTaskMock() throws Exception {
        log.info("testUpdateTaskMock is starting");
        UUID taskId = UUID.randomUUID();
        taskResponse = new TaskResponse(taskId, "Updated Task", "Updated Description",null,TaskStatus.IN_PROGRESS);
        when(taskService.updateTask(any(UUID.class), any(TaskUpdater.class))).thenReturn(taskResponse);

        mockMvc.perform(put("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskUpdater)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Updated Task"));
        log.info("testUpdateTaskMock is over successfully");
    }


    /***================      /tasks GET API  << MOCKITO >> testing     *============**/
    @Test
    public void testGetAllTasksMock() throws Exception {
        log.info("testGetAllTasksMock is starting");
        when(taskService.getAllTasks()).thenReturn(Set.of(taskResponse));

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskResponse.id().toString()))
                .andExpect(jsonPath("$[0].title").value(taskResponse.title()));
        log.info("testGetAllTasksMock is over successfully");
    }


    /***================      /tasks/{id} GET API  << MOCKITO >> testing     *============**/
    @Test
    public void testGetSingleTaskMock() throws Exception {
        log.info("testGetSingleTaskMock is starting");
        UUID taskId = taskResponse.id();
        when(taskService.getSingleTask(any(UUID.class))).thenReturn(taskResponse);

        mockMvc.perform(get("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value(taskResponse.title()));
        log.info("testGetSingleTaskMock is over successfully");
    }

    /***================      /tasks/{id} DELETE API  << MOCKITO >> testing     *============**/
    @Test
    public void testDeleteTaskMock() throws Exception {
        log.info("testDeleteTaskMock is starting");
        UUID taskId = UUID.randomUUID();
        when(taskService.deleteTask(any(UUID.class))).thenReturn("Task deleted successfully");

        mockMvc.perform(delete("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully"));
        log.info("testDeleteTaskMock is over successfully");
    }

}
