package transaction;

import exception.TransactionQueueIsFullException;
import settings.TransactionSettings;

import java.util.HashMap;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class TransactionManager implements ITransactionManager {
    private final ThreadPoolExecutor executorService;

    public TransactionManager(TransactionSettings settings) {
        executorService = new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue<Runnable>(settings.getCapacity(),settings.isFair()));

    }

    @Override
    public <T> Future<T> queue(ITransaction<T> transaction, HashMap<String, Object> context) {
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
