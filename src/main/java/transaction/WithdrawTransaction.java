package transaction;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dao.IRepository;
import dao.dto.AccountDto;
import service.Account;

import java.math.BigDecimal;
import java.util.Map;

public class WithdrawTransaction implements ITransaction<Void> {
    private final IRepository<Long, AccountDto> repository;

    @Inject
    public WithdrawTransaction(@Named("Account")IRepository<Long, AccountDto> repository) {
        this.repository = repository;
    }

    @Override
    public Void Run(Map<String, Object> context) {
        Long id = (Long)context.get("id");
        if(id == null) throw new IllegalArgumentException("Context should have account id to withdraw");
        BigDecimal amount = (BigDecimal)context.get("amount");
        if(amount == null) throw new IllegalArgumentException("Context should have amount to withdraw");

        Account account = Account.from(repository.get(id));
        account.withdraw(amount);
        repository.update(id, AccountDto.from(account));

        return null;
    }
}
