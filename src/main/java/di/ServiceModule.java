package di;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import dao.IRepository;
import dao.IUniqueGenerator;
import dao.LongUniqueGenerator;
import dao.AccountRepository;
import dao.dto.AccountDto;
import service.AccountService;
import service.IAccountService;
import transaction.ITransactionManager;
import transaction.TransactionManager;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<IUniqueGenerator<Long>>(){}).annotatedWith(Names.named("LongAtomic")).to(new TypeLiteral<LongUniqueGenerator>(){});
        bind(new TypeLiteral<IRepository<Long,AccountDto>>(){}).annotatedWith(Names.named("Account")).to(new TypeLiteral<AccountRepository>(){});
        bind(ITransactionManager.class).to(TransactionManager.class);
        bind(IAccountService.class).to(AccountService.class);
    }
}
