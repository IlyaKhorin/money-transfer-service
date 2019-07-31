package api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import di.ApiModule;
import di.ConfigurationModule;
import di.ServiceModule;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import settings.WebServerSettings;

/**
 * Base class for API setup
 */
public class ApiTestBase {

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