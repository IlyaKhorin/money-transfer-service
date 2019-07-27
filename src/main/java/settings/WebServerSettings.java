package settings;

public class WebServerSettings {
    private int port;
    private String serverName;

    public WebServerSettings(int port, String serverName) {
        this.port = port;
        this.serverName = serverName;
    }

    public WebServerSettings() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
