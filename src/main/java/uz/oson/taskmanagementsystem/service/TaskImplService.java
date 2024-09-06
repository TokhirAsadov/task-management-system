package uz.oson.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.oson.taskmanagementsystem.entity.Task;
import uz.oson.taskmanagementsystem.payload.TaskCreator;
import uz.oson.taskmanagementsystem.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskImplService implements TaskService{

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(TaskCreator taskCreator) {
        Task save = taskRepository.save(Task.builder()
                .title(taskCreator.title())
                .description(taskCreator.description())
                .dueDate(taskCreator.dueDate())
                .build());
        return save;
    }
}
