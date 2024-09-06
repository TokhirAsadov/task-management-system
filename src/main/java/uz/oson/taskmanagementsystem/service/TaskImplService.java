package uz.oson.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.oson.taskmanagementsystem.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskImplService implements TaskService{

    private final TaskRepository taskRepository;
}
