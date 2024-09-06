package uz.oson.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.oson.taskmanagementsystem.entity.Task;
import uz.oson.taskmanagementsystem.entity.TaskStatus;
import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.payload.TaskResponse;
import uz.oson.taskmanagementsystem.repository.TaskRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskImplService implements TaskService{

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskCreator taskCreator) {
        Task save = taskRepository.save(Task.builder()
                .title(taskCreator.title())
                .description(taskCreator.description())
                .dueDate(taskCreator.dueDate())
                .status(TaskStatus.OPEN)
                .build());
        return new TaskResponse(save.getId(), save.getTitle(), save.getDescription(), save.getDueDate(), save.getStatus());
    }

    @Override
    public Set<TaskResponse> getAllTasks() {
        Set<TaskResponse> taskResponses = new HashSet<>();
        taskRepository.findAll().forEach(task -> {
            taskResponses.add(new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getStatus()
            ));
        });

        return taskResponses;
    }
}
