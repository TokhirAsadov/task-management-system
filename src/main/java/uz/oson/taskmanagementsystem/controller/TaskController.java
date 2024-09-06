package uz.oson.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.oson.taskmanagementsystem.exceptions.DataNotFoundException;
import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;
import uz.oson.taskmanagementsystem.payload.TaskUpdater;
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
    public EntityModel<TaskResponse> taskCreating(@RequestBody @Valid TaskCreator creator){
        log.info("Saving new task is starting, task creator is: {}",creator);
        TaskResponse response = taskService.createTask(creator);
        log.info("Saving new task is over, task id: {}", response.id());

        EntityModel<TaskResponse> entityModel = EntityModel.of(response);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSingleTask(response.id()));
        entityModel.add(link.withRel("this-task"));
        return entityModel;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TaskResponse> taskUpdating(@PathVariable UUID id,@RequestBody @Valid TaskUpdater updater){
        log.info("Updating new task is starting,id: {}, task updater is: {}",id,updater);
        TaskResponse response = taskService.updateTask(id,updater);
        log.info("Updating new task is over, task id: {}", response.id());

        EntityModel<TaskResponse> entityModel = EntityModel.of(response);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSingleTask(response.id()));
        entityModel.add(link.withRel("this-task"));
        return entityModel;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TaskResponse> getSingleTask(@PathVariable UUID id){
        TaskResponse taskResponse = taskService.getSingleTask(id);
        if (taskResponse == null) {
            throw new DataNotFoundException("id: "+ id);
        }

        EntityModel<TaskResponse> entityModel = EntityModel.of(taskResponse);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllTasks());
        entityModel.add(link.withRel("all-tasks"));
        return entityModel;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deletingTask(@PathVariable UUID id){
        return taskService.deleteTask(id);
    }

}
