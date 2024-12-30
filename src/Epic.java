import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> allTaskId;
    private TaskManager taskManager = new TaskManager();

    public Epic(String name, String description, int id) {
        super(id,name, description, Status.NEW);
    }

    public ArrayList<Integer> getAllTaskId() {
        return allTaskId;
    }

    public void addSubtask(int subtaskId){
        allTaskId.add(subtaskId);
    }
    public void updateStatus(){
        int counterDone = 0;
        int counterNew = 0;
        for (Integer subtaskId: allTaskId){
            Subtask subtask = taskManager.getSubtask(subtaskId);
            if(subtask!=null && subtask.getStatus() == Status.DONE){
                counterDone++;
            }
        }

        for (Integer subtaskId: allTaskId){
            Subtask subtask = taskManager.getSubtask(subtaskId);
            if(subtask!=null && subtask.getStatus() == Status.NEW){
                counterNew++;
            }
        }
        if (allTaskId.size()==0 || (counterNew==allTaskId.size() && counterNew>0)){
            this.setStatus(Status.NEW);
        } else if (counterDone==allTaskId.size() && counterDone>0){
            this.setStatus(Status.DONE);
        } else {
            this.setStatus(Status.IN_PROGRESS);
        }
    }
}
