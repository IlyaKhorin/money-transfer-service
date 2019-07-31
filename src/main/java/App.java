import api.IWebServer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import di.ApiModule;
import di.ConfigurationModule;
import di.ServiceModule;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(
                new ConfigurationModule(),
                new ServiceModule(),
                new ApiModule());
        IWebServer webServer = injector.getInstance(IWebServer.class);
        webServer.start();
        System.in.read();
    }
}
