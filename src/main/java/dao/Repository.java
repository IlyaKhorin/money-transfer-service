package dao;

import com.google.inject.Inject;
import exception.NotFoundException;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Generic implementation of thread safe in-memory repository
 * @param <TKey>
 * @param <TValue>
 */
public class Repository<TKey, TValue extends IKeyedValue<TKey>> implements IRepository<TKey, TValue> {

    private IUniqueGenerator<TKey> keyGenerator;
    private ConcurrentHashMap<TKey,TValue> storage = new ConcurrentHashMap<>();

    @Inject
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
    public Collection<TValue> getAll() {
        return storage.values();
    }

    @Override
    public TValue add(TValue value) {
        TKey key = keyGenerator.getNext();
        value.setId(key);
        storage.put(key, value);
        return value;
    }

    @Override
    public TValue update(TKey id, TValue value) {
        storage.computeIfPresent(id, (tKey, tValue) -> value);
        return value;
    }

    @Override
    public void delete(TKey id) {
        storage.remove(id);
    }
}
