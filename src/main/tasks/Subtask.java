package main.tasks;

import main.constants.Status;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private final int epicId;
    private Duration duration;
    private LocalDateTime startTime;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status, int epicId, long duration) {
        super(name, description, status);
        this.epicId = epicId;
        this.duration = Duration.ofMinutes(duration);
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public int getEpicId() {
        return epicId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
