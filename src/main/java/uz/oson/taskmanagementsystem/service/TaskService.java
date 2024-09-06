package uz.oson.taskmanagementsystem.service;

import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;
import uz.oson.taskmanagementsystem.payload.TaskUpdater;

import java.util.Set;
import java.util.UUID;

public interface TaskService {
    TaskResponse createTask(TaskCreator taskCreator);
    TaskResponse updateTask(TaskUpdater taskUpdater);

    Set<TaskResponse> getAllTasks();

    TaskResponse getSingleTask(UUID id);
    String deleteTask(UUID id);
}
