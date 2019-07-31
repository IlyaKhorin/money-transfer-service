package service;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import dao.IRepository;
import dao.dto.AccountDto;
import transaction.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class AccountService implements IAccountService{
    private final ITransactionManager transactionManager;
    private final IRepository<Long, AccountDto> repository;
    private final Injector injector;

    @Inject
    public AccountService(ITransactionManager transactionManager,
                          @Named("Account") IRepository<Long, AccountDto> repository,
                          //Using an injector here is antipattern but it's done for simplicity. Here I could use Factory instead
                          Injector injector) {
        this.transactionManager = transactionManager;
        this.repository = repository;
        this.injector = injector;
    }


    @Override
    public Account create() {
        AccountDto accountDto = new AccountDto();
        accountDto.setBalance(BigDecimal.valueOf(0));
        return Account.from(repository.add(accountDto));
    }

    @Override
    public Account get(long id) {
        return Account.from(repository.get(id));
    }

    @Override
    public void delete(long id) throws InterruptedException, ExecutionException {
        Map<String, Object> context = new HashMap<>();
        context.put("id", id);
        transactionManager.submit(injector.getInstance(DeleteAccountTransaction.class), context).get();
    }

    @Override
    public void withdraw(long id, BigDecimal amount) throws InterruptedException, ExecutionException {
        Map<String, Object> context = new HashMap<>();
        context.put("id", id);
        context.put("amount", amount);
        safeSubmit(injector.getInstance(WithdrawTransaction.class), context);
    }

    private <T> void safeSubmit(ITransaction<T> transaction, Map<String, Object> context) throws InterruptedException, ExecutionException {
        try {
            transactionManager.submit(transaction, context).get();
        } catch (ExecutionException e) {
            if(e.getCause() instanceof  RuntimeException){
                throw (RuntimeException)e.getCause();
            }
            throw e;
        }
    }

    @Override
    public void deposit(long id, BigDecimal amount) throws InterruptedException, ExecutionException {
        Map<String, Object> context = new HashMap<>();
        context.put("id", id);
        context.put("amount", amount);
        safeSubmit(injector.getInstance(DepositTransaction.class), context);
    }

    @Override
    public void transfer(long fromId, long toId, BigDecimal amount) throws ExecutionException, InterruptedException {
        Map<String, Object> context = new HashMap<>();
        context.put("fromId", fromId);
        context.put("toId", toId);
        context.put("amount", amount);
        safeSubmit(injector.getInstance(TransferTransaction.class), context);
    }

    @Override
    public List<Account> getAll() {
        return repository.getAll().stream().map(Account::from).collect(Collectors.toList());
    }
}
