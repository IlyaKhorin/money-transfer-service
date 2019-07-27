package dao;

import exception.NotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Generic implementation of thread safe in-memory repository
 * @param <TKey>
 * @param <TValue>
 */
public class Repository<TKey, TValue extends IKeyedValue<TKey>> implements IRepository<TKey, TValue> {

    private IUniqueGenerator<TKey> keyGenerator;
    private ConcurrentHashMap<TKey,TValue> storage = new ConcurrentHashMap<>();

    public Repository(IUniqueGenerator<TKey> keyGenerator) {
        this.keyGenerator = keyGenerator;
    }


    @Override
    public TValue get(TKey id) {
        TValue value = storage.get(id);
        if (value == null) {
            throw new NotFoundException("Element with id "+ id +" was not found in repository");
        }
        return value;
    }

    @Override
    public TKey add(TValue value) {
        TKey key = keyGenerator.getNext();
        value.setId(key);
        storage.put(key, value);
        return key;
    }

    @Override
    public void update(TKey id, Function<TValue,TValue> updateFunc) {
        storage.computeIfPresent(id, (tKey, tValue) -> updateFunc.apply(tValue));
    }

    @Override
    public void delete(TKey id) {
        storage.remove(id);
    }
}
