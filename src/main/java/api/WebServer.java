package api;

import com.google.inject.Inject;
import settings.WebServerSettings;
import spark.Spark;

import java.util.Set;

import static spark.Spark.before;
import static spark.Spark.port;

public class WebServer implements IWebServer {
    private final WebServerSettings settings;
    private final Set<IApiController> controllers;

    @Inject
    public WebServer(WebServerSettings settings, Set<IApiController> controllers) {
        this.settings = settings;
        this.controllers = controllers;
    }

    @Override
    public void start() {
        port(settings.getPort());
        before((req, res) -> res.type("application/json"));
        for (IApiController controller : controllers) {
            controller.setUp();
        }
    }

    @Override
    public void stop() {
        Spark.stop();
    }
}
