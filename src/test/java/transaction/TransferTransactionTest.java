package transaction;

import dao.IRepository;
import dao.dto.AccountDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferTransactionTest {

    private TransferTransaction sut;

    @Mock
    public IRepository<Long, AccountDto> repository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    private ArgumentCaptor<AccountDto> accountToArgumentCaptor;
    @Captor
    private ArgumentCaptor<AccountDto> accountFromArgumentCaptor;

    @Before
    public void setUp(){
        sut = new TransferTransaction(repository);
    }

    @Test
    public void run_emptyIdToShouldThrow() {
        Map<String, Object> context = new HashMap<>();
        Long idFrom = 123L;
        BigDecimal amount = new BigDecimal(100);
        context.put("amount", amount);
        context.put("fromId", idFrom);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Context should have account toId to transfer");
        sut.Run(context);
    }

    @Test
    public void run_emptyIdFromShouldThrow() {
        Map<String, Object> context = new HashMap<>();
        Long idTo = 123L;
        BigDecimal amount = new BigDecimal(100);
        context.put("amount", amount);
        context.put("toId", idTo);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Context should have account fromId to transfer");
        sut.Run(context);
    }

    @Test
    public void run_emptyAmountShouldThrow() {
        Map<String, Object> context = new HashMap<>();
        Long idTo = 123L;
        Long idFrom = 124L;
        context.put("toId", idTo);
        context.put("fromId", idFrom);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Context should have amount to transfer");

        sut.Run(context);
    }

    @Test
    public void run_shouldUpdateRepository() {
        Map<String, Object> context = new HashMap<>();
        final Long idTo = 123L;
        final Long idFrom = 124L;
        final BigDecimal amount = new BigDecimal(100);
        final BigDecimal balanceFrom = new BigDecimal(222);
        final BigDecimal balanceTo = new BigDecimal(333);
        final BigDecimal expectedToBalance = amount.add(balanceTo);
        final BigDecimal expectedFromBalance = balanceFrom.subtract(amount);
        context.put("toId", idTo);
        context.put("fromId", idFrom);
        context.put("amount", amount);

        AccountDto toAccount = new AccountDto();
        toAccount.setId(idTo);
        toAccount.setBalance(balanceTo);

        AccountDto fromAccount = new AccountDto();
        fromAccount.setId(idFrom);
        fromAccount.setBalance(balanceFrom);

        when(repository.get(idTo)).thenReturn(toAccount);
        when(repository.get(idFrom)).thenReturn(fromAccount);

        sut.Run(context);

        verify(repository).update(eq(idTo), accountToArgumentCaptor.capture());
        assertEquals(expectedToBalance, accountToArgumentCaptor.getValue().getBalance());

        verify(repository).update(eq(idFrom), accountFromArgumentCaptor.capture());
        assertEquals(expectedFromBalance, accountFromArgumentCaptor.getValue().getBalance());
    }
}