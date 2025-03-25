package main.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import main.constants.Status;

public class Epic extends Task {

    protected final ArrayList<Subtask> allSubtasks = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, Status.NEW, Duration.ZERO, null);
        this.endTime = null;
    }

    public Epic(int id, String name, String description) {
        super(name, description, Status.NEW, Duration.ZERO, null);
        this.id = id;
        this.endTime = null;
    }

    public void updateEpicTime() {
        if (allSubtasks.isEmpty()) {
            this.duration = Duration.ZERO;
            this.startTime = null;
            this.endTime = null;
            return;
        }
        this.startTime = getStartTime();
        this.endTime = getEndTime();
        this.duration = getDuration();

    }

    public Duration getDuration() {
        if (this.startTime != null && this.endTime != null) {
            return Duration.between(this.startTime, this.endTime);
        } else {
            return Duration.ZERO;
        }
    }

    public LocalDateTime getStartTime() {
        return allSubtasks.stream()
                .filter(Objects::nonNull)
                .filter(subtask -> subtask.getStartTime() != null && subtask.getEndTime() != null)
                .map(Subtask::getStartTime)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    public LocalDateTime getEndTime() {
        return allSubtasks.stream()
                .filter(Objects::nonNull)
                .filter(subtask -> subtask.getEndTime() != null)
                .map(Subtask::getEndTime)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    public void addSubtask(Subtask subtask) {
        allSubtasks.add(subtask);
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return allSubtasks;
    }

    public void updateEpicStatus() {
        int countNew = 0;
        int countDone = 0;
        for (Subtask subtask : allSubtasks) {
            if (subtask != null && subtask.getStatus() == Status.NEW) {
                countNew++;
            } else if (subtask != null && subtask.getStatus() == Status.DONE) {
                countDone++;
            }
        }
        if (allSubtasks.size() == 0 || (allSubtasks.size() == countNew && countNew > 0)) {
            setStatus(Status.NEW);
        } else if (countDone > 0 && allSubtasks.size() == countDone) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
    }

    public void deleteFromEpicSubtasks(Subtask subtask){
        allSubtasks.remove(subtask);
    }
}
