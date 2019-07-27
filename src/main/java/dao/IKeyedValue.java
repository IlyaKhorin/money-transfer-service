package dao;

public interface IKeyedValue<TKey> {
    TKey getId();
    void setId(TKey key);
}
