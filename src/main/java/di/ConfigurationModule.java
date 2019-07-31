package di;

import com.google.inject.AbstractModule;
import settings.TransactionSettings;
import settings.WebServerSettings;

public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(WebServerSettings.class).toInstance(new WebServerSettings(12000));
        bind(TransactionSettings.class).toInstance(new TransactionSettings(1000, true));
    }
}
