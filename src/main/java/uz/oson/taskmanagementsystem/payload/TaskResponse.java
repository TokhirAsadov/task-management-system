package uz.oson.taskmanagementsystem.payload;

import uz.oson.taskmanagementsystem.entity.TaskStatus;

import java.sql.Timestamp;
import java.util.UUID;

public record TaskResponse(UUID id, String title, String description, Timestamp dueDate, TaskStatus status) {
}
