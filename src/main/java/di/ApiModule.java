package di;

import com.google.inject.AbstractModule;
import dao.IRepository;
import dao.Repository;
import transaction.ITransactionManager;
import transaction.TransactionManager;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IRepository.class).to(Repository.class);
        bind(ITransactionManager.class).to(TransactionManager.class);
    }
}
