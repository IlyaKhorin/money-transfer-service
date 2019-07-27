package settings;

public class TransactionSettings {
    private int capacity;
    private boolean fair;

    public TransactionSettings(int capacity, boolean fair) {
        this.capacity = capacity;
        this.fair = fair;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFair() {
        return fair;
    }

    public void setFair(boolean fair) {
        this.fair = fair;
    }
}
