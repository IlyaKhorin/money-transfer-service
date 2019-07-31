package service;

import exception.InsufficientFundsException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    private BigDecimal initialBalance = BigDecimal.valueOf(100);
    private Account sut;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void setUp(){
        long id = 1;
        sut = new Account(id,initialBalance);
    }

    @Test
    public void withdraw_shouldDecreaseBalance() {
        BigDecimal amount = BigDecimal.valueOf(50);
        BigDecimal expectedBalance = initialBalance.subtract(amount);
        sut.withdraw(amount);
        assertEquals(expectedBalance,sut.getBalance());
    }

    @Test
    public void withdraw_shouldThrowInsufficientFunds() {
        BigDecimal amount = initialBalance.add(BigDecimal.valueOf(50L));

        expectedException.expect(InsufficientFundsException.class);
        expectedException.expectMessage("Not possible to withdraw "+amount+" from account");
        sut.withdraw(amount);
    }

    @Test
    public void deposit_shouldIncreaseBalance() {
        BigDecimal amount = BigDecimal.valueOf(50);
        BigDecimal expectedBalance = initialBalance.add(amount);
        sut.deposit(amount);
        assertEquals(expectedBalance,sut.getBalance());
    }
}