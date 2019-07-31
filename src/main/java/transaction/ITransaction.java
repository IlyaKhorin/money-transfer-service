package transaction;

import java.util.Map;

public interface ITransaction<T> {
    T Run(Map<String,Object> context);
}
