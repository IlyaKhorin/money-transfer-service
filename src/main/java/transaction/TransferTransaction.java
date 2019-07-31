package transaction;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dao.IRepository;
import dao.dto.AccountDto;
import service.Account;

import java.math.BigDecimal;
import java.util.HashMap;

public class TransferTransaction implements ITransaction<Void> {
    private final IRepository<Long, AccountDto> repository;

    @Inject
    public TransferTransaction(@Named("Account")IRepository<Long, AccountDto> repository) {
        this.repository = repository;
    }

    @Override
    public Void Run(HashMap<String, Object> context) {
        Long fromId = (Long)context.get("fromId");
        if(fromId == null) throw new IllegalArgumentException("Context should have account fromId to transfer");
        Long toId = (Long)context.get("toId");
        if(toId == null) throw new IllegalArgumentException("Context should have account toId to transfer");
        BigDecimal amount = (BigDecimal)context.get("amount");
        if(amount == null) throw new IllegalArgumentException("Context should have amount to transfer");

        Account fromAccount = Account.from(repository.get(fromId));
        Account toAccount = Account.from(repository.get(toId));
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        repository.update(fromId, AccountDto.from(fromAccount));
        repository.update(toId, AccountDto.from(toAccount));

        return null;
    }
}
