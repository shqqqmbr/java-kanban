
public class Subtask extends Task {
    private int epicId;

    public Subtask(int name, String description, String status, Status id, int epicId) {
        super(name, description, status, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
