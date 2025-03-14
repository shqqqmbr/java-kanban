package main.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import main.constants.Status;

public class Epic extends Task {

    protected final ArrayList<Subtask> allSubtasks = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, Status.NEW, Duration.ZERO, null);
        this.duration = Duration.ZERO;
        this.startTime = null;
        this.endTime = null;
    }


    public void updateEpicTime() {
        if (allSubtasks.isEmpty()) {
            this.duration = Duration.ZERO;
            this.startTime = null;
            this.endTime = null;
            return;
        }
        this.startTime = getEpicStartTime();
        this.endTime = getEpicEndTime();
        if (startTime != null && endTime != null) {
            this.duration = Duration.between(startTime, endTime);
        } else {
            this.duration = Duration.ZERO;
        }

    }


    public LocalDateTime getEpicStartTime() {
        return allSubtasks.stream()
                .filter(subtask -> subtask.getStartTime() != null && subtask.getEndTime() != null)
                .map(Subtask::getStartTime)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    public LocalDateTime getEpicEndTime() {
        return allSubtasks.stream()
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
}
