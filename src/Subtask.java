
public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Status status, int id, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
