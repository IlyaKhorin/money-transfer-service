package exception;

public class TransactionQueueIsFullException extends RuntimeException {

    public TransactionQueueIsFullException(String message, Throwable ex) {
        super(message, ex);
    }
}
