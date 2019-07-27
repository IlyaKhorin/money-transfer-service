package dao;

import java.util.function.Function;

public interface IRepository<TKey, TValue extends IKeyedValue<TKey>> {
    TValue get(TKey id);
    TKey add(TValue value);
    void update(TKey id, Function<TValue,TValue> updateFunc);
    void delete(TKey id);
}
