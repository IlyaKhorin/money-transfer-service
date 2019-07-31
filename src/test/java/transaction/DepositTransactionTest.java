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
public class DepositTransactionTest {

    private DepositTransaction sut;

    @Mock
    public IRepository<Long, AccountDto> repository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    private ArgumentCaptor<AccountDto> accountArgumentCaptor;

    @Before
    public void setUp(){
        sut = new DepositTransaction(repository);
    }

    @Test
    public void run_emptyIdShouldThrow() {
        Map<String, Object> context = new HashMap<>();
        BigDecimal amount = new BigDecimal(100);
        context.put("amount", amount);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Context should have account id to deposit");
        sut.Run(context);
    }

    @Test
    public void run_emptyAmountShouldThrow() {
        Map<String, Object> context = new HashMap<>();
        Long id = 123L;
        context.put("id", id);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Context should have amount to deposit");

        sut.Run(context);
    }

    @Test
    public void run_shouldUpdateRepository() {
        Map<String, Object> context = new HashMap<>();
        final Long id = 123L;
        final BigDecimal amount = new BigDecimal(100);
        final BigDecimal balance = new BigDecimal(100);
        final BigDecimal expectedBalance = amount.add(balance);
        context.put("id", id);
        context.put("amount", amount);

        AccountDto testAccount = new AccountDto();
        testAccount.setId(id);
        testAccount.setBalance(balance);

        when(repository.get(id)).thenReturn(testAccount);

        sut.Run(context);

        verify(repository).update(eq(id), accountArgumentCaptor.capture());
        assertEquals(expectedBalance, accountArgumentCaptor.getValue().getBalance());
    }
}