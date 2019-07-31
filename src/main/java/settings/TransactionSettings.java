package settings;

/**
 * Represents general settings for {@link transaction.TransactionManager}
 */
public class TransactionSettings {
    private int capacity;
    private boolean fair;

    public TransactionSettings(int capacity, boolean fair) {
        this.capacity = capacity;
        this.fair = fair;
    }

    /**
     * @return Returns maximum capacity of transaction queue
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return Returns flag whether transaction queue should be ordered or not
     */
    public boolean isFair() {
        return fair;
    }
}
