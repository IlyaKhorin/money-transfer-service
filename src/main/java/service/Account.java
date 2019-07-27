package service;

import dao.dto.AccountDto;
import exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

public final class Account {
    private long id;
    private BigDecimal balance;

    public Account(long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidParameterException("Amount to withdraw should be non negative");
        }
        BigDecimal newBalance = balance.subtract(amount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new InsufficientFundsException("Not possible to withdraw " + amount + " from account");
        }
        balance = newBalance;
    }

    public void deposit(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidParameterException("Amount to deposit should be non negative");
        }
        BigDecimal newBalance = balance.add(amount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new InsufficientFundsException("Not possible to withdraw " + amount + " from account");
        }
        balance = newBalance;
    }

    public static Account from(AccountDto value) {
        return new Account(value.getId(), value.getBalance());
    }
}
