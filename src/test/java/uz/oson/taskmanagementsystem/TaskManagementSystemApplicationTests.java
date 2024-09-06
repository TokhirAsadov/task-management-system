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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
