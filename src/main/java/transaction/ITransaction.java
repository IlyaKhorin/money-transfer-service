package transaction;

import java.util.HashMap;

public interface ITransaction<T> {
    T Run(HashMap<String,Object> context);
}
