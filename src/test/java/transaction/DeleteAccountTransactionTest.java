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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccountTransactionTest {

    private DeleteAccountTransaction sut;

    @Mock
    public IRepository<Long, AccountDto> repository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){
        sut = new DeleteAccountTransaction(repository);
    }

    @Test
    public void run_emptyIdShouldThrow() {
        HashMap<String, Object> context = new HashMap<>();

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Context should have account id to delete");
        sut.Run(context);
    }

    @Test
    public void run_shouldUpdateRepository()  {
        HashMap<String, Object> context = new HashMap<>();
        final Long id = 123L;
        context.put("id", id);

        sut.Run(context);

        verify(repository).delete(eq(id));
    }
}