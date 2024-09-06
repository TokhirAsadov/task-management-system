package uz.oson.taskmanagementsystem.service;

import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;

import java.util.Set;

public interface TaskService {
    TaskResponse createTask(TaskCreator taskCreator);

    Set<TaskResponse> getAllTasks();
}
