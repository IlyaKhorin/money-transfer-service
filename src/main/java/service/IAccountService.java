package service;

import java.util.List;

public interface IAccountService {
    Account create(double balance);

    Account get(long id);

    void delete(long id);

    void withdraw(long id, double amount);

    void deposit(long id, double amount);

    void transfer(long fromId, long toId, double amount);

    List<Account> getAll();
}
