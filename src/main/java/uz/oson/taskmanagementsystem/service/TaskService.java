package uz.oson.taskmanagementsystem.service;

import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;

import java.util.Set;
import java.util.UUID;

public interface TaskService {
    TaskResponse createTask(TaskCreator taskCreator);

    Set<TaskResponse> getAllTasks();

    TaskResponse getSingleTask(UUID id);
}
