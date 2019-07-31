package settings;

/**
 * Represents general web settings
 */
public class WebServerSettings {
    private int port;

    public WebServerSettings(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

}
