package transaction;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dao.IRepository;
import dao.dto.AccountDto;
import service.Account;

import java.math.BigDecimal;
import java.util.HashMap;

public class DepositTransaction implements ITransaction<Void> {
    private final IRepository<Long, AccountDto> repository;

    @Inject
    public DepositTransaction(@Named("Account")IRepository<Long, AccountDto> repository) {
        this.repository = repository;
    }

    @Override
    public Void Run(HashMap<String, Object> context) {
        Long id = (Long)context.get("id");
        if(id == null) throw new IllegalArgumentException("Context should have account id to deposit");
        BigDecimal amount = (BigDecimal)context.get("amount");
        if(amount == null) throw new IllegalArgumentException("Context should have amount to deposit");

        Account account = Account.from(repository.get(id));
        account.deposit(amount);
        repository.update(id, AccountDto.from(account));

        return null;
    }
}
