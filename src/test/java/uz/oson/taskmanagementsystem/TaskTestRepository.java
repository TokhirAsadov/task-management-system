package uz.oson.taskmanagementsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oson.taskmanagementsystem.entity.Task;

import java.util.UUID;

public interface TaskTestRepository extends JpaRepository<Task, UUID> {
}
