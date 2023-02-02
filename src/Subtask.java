public class Subtask extends Task{
    int epicId;

    public Subtask(int id, String title, String description, String status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }
}
