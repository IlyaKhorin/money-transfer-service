package transaction;

import java.util.HashMap;
import java.util.concurrent.Future;

public interface ITransactionManager {
    /**
     * Put transaction in execution queue to be executed in safe mode.
     * @param transaction Transaction to be executed
     * @param context Context to be an input for transaction
     * @param <T> Type of return element
     * @return {@link Future} which represents queued execution of transaction
     */
    <T> Future<T> submit(ITransaction<T> transaction, HashMap<String, Object> context);
}
