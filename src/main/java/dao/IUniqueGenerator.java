package dao;

public interface IUniqueGenerator<T> {
    /**
     * @return return new unique value
     */
    T getNext();
}
