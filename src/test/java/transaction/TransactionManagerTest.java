package transaction;

import exception.TransactionQueueIsFullException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import settings.TransactionSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TransactionManagerTest {

    private TransactionSettings settings = new TransactionSettings(1,true);
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private TransactionManager sut;

    @Before
    public void setUp(){
        sut = new TransactionManager(settings);
    }

    @Test
    public void queue_shouldCompleteTask() throws ExecutionException, InterruptedException {
        int expectedResult = 123;
        Map<String, Object> context = new HashMap<>();
        context.put("value", expectedResult);

        Future<Object> future = sut.submit(c -> c.get("value"), context);

        assertEquals(expectedResult,future.get());
    }

    @Test
    public void queue_shouldThrowQueueOverflow() {
        Map<String, Object> context = new HashMap<>();

        Future<Object> future = sut.submit(c -> {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException ignored) {
            }
            return null;
        }, context);

        expectedException.expect(TransactionQueueIsFullException.class);
        assertFalse(future.isDone());
        sut.submit(c -> null,context);
        sut.submit(c -> null,context);
    }

    @After
    public void tearDown()  {
        sut.shutdown();
    }
}