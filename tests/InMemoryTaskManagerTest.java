import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void BeforeEach() throws IOException {
        taskManager = new InMemoryTaskManager();
        initTasks();
    }
}
