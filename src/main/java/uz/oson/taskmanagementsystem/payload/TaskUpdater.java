package uz.oson.taskmanagementsystem.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import uz.oson.taskmanagementsystem.entity.TaskStatus;

import java.sql.Timestamp;
import java.util.UUID;

public record TaskUpdater(
        @NotNull(message = "Title should be not null")
        @Size(min = 1, max = 255, message = "Title should have at least 2 characters")
        String title,
        String description,
        Timestamp dueDate,
        @NotNull(message = "Status should be not null; It can be once of OPEN, IN_PROGRESS, COMPLETED")
        TaskStatus status
        ) {
}
