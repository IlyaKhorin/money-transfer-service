package di;

import com.google.inject.AbstractModule;
import dao.IRepository;
import dao.Repository;
import settings.TransactionSettings;
import settings.WebServerSettings;

public class ConfigrationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(WebServerSettings.class).toInstance(new WebServerSettings(12000));
        bind(TransactionSettings.class).toInstance(new TransactionSettings(1000, true));
    }
}
