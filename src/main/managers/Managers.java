package main.managers;

public final class Managers {
    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }
}
