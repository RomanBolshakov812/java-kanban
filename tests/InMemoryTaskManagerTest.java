import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void BeforeEachT() {
        T = new InMemoryTaskManager();
    }
}
