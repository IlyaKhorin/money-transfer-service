package api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import di.ApiModule;
import di.ConfigurationModule;
import di.ServiceModule;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import service.Account;
import service.AccountService;
import service.IAccountService;
import settings.WebServerSettings;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * Base class for API setup
 */
public class ApiTestBase {

    private static final int PORT = 1234;
    private static IWebServer webServer;

    @BeforeClass
    public static void setUp() {
        Injector injector = Guice.createInjector(
                new ConfigurationModule(),
                new ServiceModule(),
                new ApiModule());
        webServer = injector.getInstance(IWebServer.class);
        webServer.start();
        RestAssured.baseURI = "http://localhost:" + injector.getInstance(WebServerSettings.class).getPort();
    }

    @AfterClass
    public static void tearDown() {
        webServer.stop();
    }
}