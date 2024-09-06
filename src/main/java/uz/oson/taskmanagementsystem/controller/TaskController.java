package uz.oson.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;
import uz.oson.taskmanagementsystem.service.TaskService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String taskCreating(@RequestBody @Valid TaskCreator creator){
        log.info("Saving new task is starting, task creator is: {}",creator);
        TaskResponse response = taskService.createTask(creator);
        log.info("Saving new task is over, task id: {}", response.id());

        return "task is created successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getSingleTask(@PathVariable UUID id){
        return taskService.getSingleTask(id);
    }

}
