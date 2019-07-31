package service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public interface IAccountService {
    Account create();

    Account get(long id);

    void delete(long id) throws ExecutionException, InterruptedException;

    void withdraw(long id, BigDecimal amount) throws InterruptedException, ExecutionException;

    void deposit(long id, BigDecimal amount) throws ExecutionException, InterruptedException;

    void transfer(long fromId, long toId, BigDecimal amount) throws ExecutionException, InterruptedException;

    Collection<Account> getAll();
}
