package transaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exception.TransactionQueueIsFullException;
import settings.TransactionSettings;

import java.util.HashMap;
import java.util.concurrent.*;


/**
 * Single threaded transaction executor. In small financial app it makes sense to have
 * single threaded executor which guarantees fair, ordered, thread safe execution of financial transaction.
 * This approach gives advantages in case of many different caches involved in atomic operation.
 * Easy to maintain, debug. Easy to extend with persistence, transaction rollback, "exactly once" guarantee.
 */
@Singleton
public class TransactionManager implements ITransactionManager {
    private final ThreadPoolExecutor executorService;

    @Inject
    public TransactionManager(TransactionSettings settings) {
        executorService = new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue<Runnable>(settings.getCapacity(),settings.isFair()));
    }

    @Override
    public <T> Future<T> submit(ITransaction<T> transaction, HashMap<String, Object> context) {
        try {
            return executorService.submit(() -> transaction.Run(context));
        } catch (RejectedExecutionException ex){
            throw new TransactionQueueIsFullException("Transaction queue is full", ex);
        }
    }

    public void shutdown(){
        executorService.shutdown();
    }
}
