package main.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import main.constants.Status;

public class Epic extends Task {

    private final ArrayList<Subtask> allSubtasks = new ArrayList<>();

    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public Epic(String name, String description, Duration duration) {
        super(name, description, Status.NEW);
        this.duration = duration;
    }

    public LocalDateTime getEndTime(){
        LocalDateTime endTime = allSubtasks.stream()
                .filter()
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
