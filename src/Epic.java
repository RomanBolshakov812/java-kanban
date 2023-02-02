import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    ArrayList<Integer> subtasksId;
    HashMap<Integer, String> subtasksStatus;

    public Epic(int id, String title, String description, String status, ArrayList<Integer> subtasksId, HashMap<Integer,
            String> subtasksStatus) {
        super(id, title, description, status);
        this.subtasksId = subtasksId;
        this.subtasksStatus = subtasksStatus;
    }
}
