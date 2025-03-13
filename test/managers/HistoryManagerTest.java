package managers;

import main.managers.HistoryManager;
import main.managers.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;

public abstract class HistoryManagerTest<T extends HistoryManager> {
    protected T manager;

    protected abstract T createTaskManager();

    @BeforeEach
    public void beforeEach(){
        manager = createTaskManager();
    }

}
