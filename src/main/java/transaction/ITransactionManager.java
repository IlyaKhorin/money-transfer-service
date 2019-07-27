package transaction;

import java.util.HashMap;
import java.util.concurrent.Future;

public interface ITransactionManager {
    <T> Future<T> queue(ITransaction<T> transaction, HashMap<String, Object> context);
}
