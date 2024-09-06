package uz.oson.taskmanagementsystem.service;

import uz.oson.taskmanagementsystem.entity.Task;
import uz.oson.taskmanagementsystem.payload.TaskCreator;

public interface TaskService {
    Task createTask(TaskCreator taskCreator);
}
