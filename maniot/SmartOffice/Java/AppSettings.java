//package restcommunicator;

public class AppSettings {
    private String midiaUser;
    private String midiaPassword;
    private String databaseServerIP;
    private String databaseUser;
    private String databasePassword;
    private String restMLServerIP;
    private String restMLServerPort;

    public AppSettings() {
        this.midiaUser = "";
        this.midiaPassword = "";
        this.databaseServerIP = "";
        this.databaseUser = "";
        this.databasePassword = "";
        this.restMLServerIP = "";
        this.restMLServerPort = "";
    }

    public String getMidiaUser() {
        return midiaUser;
    }

    public void setMidiaUser(String user) {
        this.midiaUser = user;
    }

    public String getMidiaPassword() {
        return midiaPassword;
    }

    public void setMidiaPassword(String password) {
        this.midiaPassword = password;
    }

    public String getDatabaseServerIP() {
        return databaseServerIP;
    }

    public void setDatabaseServerIP(String ip) {
        this.databaseServerIP = ip;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String user) {
        this.databaseUser = user;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String password) {
        this.databasePassword = password;
    }

    public String getRestMLServerIP() {
        return restMLServerIP;
    }

    public void setRestMLServerIP(String ip) {
        this.restMLServerIP =ip;
    }

    public String getRestMLServerPort() {
        return restMLServerPort;
    }

    public void setRestMLServerPort(String port) {
        this.restMLServerPort = port;
    }

}
