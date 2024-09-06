package uz.oson.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oson.taskmanagementsystem.entity.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
