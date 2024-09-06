package uz.oson.taskmanagementsystem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import uz.oson.taskmanagementsystem.entity.Task;
import uz.oson.taskmanagementsystem.entity.TaskStatus;
import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;
import uz.oson.taskmanagementsystem.payload.TaskUpdater;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskManagementSystemApplicationTests {

    @LocalServerPort
    private int port;

//    private String baseUrl = "http://localhost:8080";
    private String baseUrl = "http://localhost:";

    private static RestTemplate restTemplate;

    @Autowired
    private TaskTestRepository taskTestRepository;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp(){
        baseUrl=baseUrl.concat(port+"/tasks");
    }


    /***================      /tasks POST API testing     *============**/
    @Test
    public void testTaskCreation(){
        TaskCreator creator = new TaskCreator("test-title","test-description",null,TaskStatus.OPEN);
        TaskResponse response = restTemplate.postForObject(baseUrl, creator, TaskResponse.class);
        assertEquals("test-title",response.title());
        assertEquals(1,taskTestRepository.findAll().size());
    }


    /***================      /tasks GET API testing     *============**/
    @Test
    @Sql(statements = "INSERT INTO TASK (id, title, description, status) VALUES (uuid(),'test-title','test-description','OPEN')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TASK WHERE title='test-title'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllTasks(){
        Set<TaskResponse> tasks = restTemplate.getForObject(baseUrl, Set.class);
        assertEquals(1,tasks.size());
        assertEquals(1,taskTestRepository.findAll().size());
    }

    /***================      /tasks/{id} GET API testing     *============**/
    @Test
    @Sql(statements = "INSERT INTO TASK (id, title, description, status) VALUES ('5985aabd-9942-4a5e-9cb2-920665068e4a','test-title','test-description','OPEN')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TASK WHERE id='5985aabd-9942-4a5e-9cb2-920665068e4a'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetSingleTask(){
        TaskResponse response = restTemplate.getForObject(baseUrl+"/{id}", TaskResponse.class,"5985aabd-9942-4a5e-9cb2-920665068e4a");
        assertAll(
                ()->assertNotNull(response),
                ()->assertEquals("5985aabd-9942-4a5e-9cb2-920665068e4a",response.id().toString()),
                ()->assertEquals("test-title",response.title())
        );

    }

    /***================      /tasks/{id} PUT API testing     *============**/
    @Test
    @Sql(statements = "INSERT INTO TASK (id, title, description, status) VALUES ('63737ff1-22c5-4f91-aa82-8c39c6d8ec89','test-title','test-description','OPEN')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TASK WHERE id='63737ff1-22c5-4f91-aa82-8c39c6d8ec89'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testTaskUpdating(){
        TaskUpdater updater = new TaskUpdater("test-update-title","test-update-description",null,TaskStatus.COMPLETED);
        restTemplate.put(baseUrl+"/{id}", updater,"63737ff1-22c5-4f91-aa82-8c39c6d8ec89");
        Task task = taskTestRepository.findById(UUID.fromString("63737ff1-22c5-4f91-aa82-8c39c6d8ec89")).get();
        assertAll(
                ()->assertNotNull(task),
                ()->assertEquals("test-update-title",task.getTitle()),
                ()->assertEquals("test-update-description",task.getDescription()),
                ()->assertEquals(TaskStatus.COMPLETED,task.getStatus())
        );
    }

    /***================      /tasks/{id} DELETE API testing     *============**/
    @Test
    @Sql(statements = "INSERT INTO TASK (id, title, description, status) VALUES ('7cc7d269-4c7b-4f20-8471-6c473f7a5ed5','test-DELETE-title','test-DELETE-description','OPEN')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeletingTask(){
        int taskCount = taskTestRepository.findAll().size();
        assertEquals(1,taskCount);
        restTemplate.delete(baseUrl+"/{id}","7cc7d269-4c7b-4f20-8471-6c473f7a5ed5");
        assertEquals(0,taskTestRepository.findAll().size());
    }

}
