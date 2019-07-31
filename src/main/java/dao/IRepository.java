package dao;

import java.util.Collection;

public interface IRepository<TKey, TValue extends IKeyedValue<TKey>> {
    TValue get(TKey id);
    Collection<TValue> getAll();
    TValue add(TValue value);
    TValue update(TKey id, TValue value);
    void delete(TKey id);
}
