package main.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import main.constants.Status;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, Status status, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
    }

    public LocalDateTime getEndTime(){
        return startTime.plus(duration);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) &&
                Objects.equals(status, task.status) && Objects.equals(id, task.id);//исправлено
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, id);//исправлено
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}' + "\n";
    }
}
