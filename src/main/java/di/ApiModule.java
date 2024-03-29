package di;

import api.AccountController;
import api.IApiController;
import api.IWebServer;
import api.WebServer;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class ApiModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IWebServer.class).to(WebServer.class);
        Multibinder<IApiController> apiBinder = Multibinder.newSetBinder(binder(), IApiController.class);
        apiBinder.addBinding().to(AccountController.class);
    }
}
