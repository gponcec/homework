public class AppSettings {
    private String dbServerIP;
    private String dbServerPort;
    private String dbUsername;
    private String dbName;
    private String dbPassword;
    private String restServerIP;
    private String restServerPort;
    private String midiaUsername;
    private String midiaPassword;
    private String acuId;
    private String userPhoneIP;

    public AppSettings() {
        this.dbServerIP = "";
        this.dbServerPort = "";
        this.dbName = "";
        this.dbUsername = "";
        this.dbPassword = "";
        this.restServerIP = "";
        this.restServerPort = "";
        this.midiaUsername = "";
        this.midiaPassword = "";
        this.acuId = "";
        this.userPhoneIP = "";
    }

    public String getDbServerIP() {
        return dbServerIP;
    }

    public void setDbServerIP(String ip) {
        this.dbServerIP = ip;
    }

    public String getDbServerPort() {
        return dbServerPort;
    }

    public void setDbServerPort(String port) {
        this.dbServerPort = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String bdName) {
        this.dbName = bdName;
    }
    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String username) {
        this.dbUsername = username;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String password) {
        this.dbPassword = password;
    }

    public String getRestServerIP() {
        return restServerIP;
    }

    public void setRestServerIP(String ip) {
        this.restServerIP =ip;
    }

    public String getRestServerPort() {
        return restServerPort;
    }

    public void setRestServerPort(String port) {
        this.restServerPort = port;
    }

    public String getMidiaUsername() {
        return midiaUsername;
    }

    public void setMidiaUsername(String username) {
        this.midiaUsername = username;
    }

    public String getMidiaPassword() {
        return midiaPassword;
    }

    public void setMidiaPassword(String password) {
        this.midiaPassword = password;
    }

    public String getAcuId() {
        return acuId;
    }

    public void setAcuId(String acuId) {
        this.acuId = acuId;
    }

    public String getUserPhoneIP() {
        return userPhoneIP;
    }

    public void setUserPhoneIP(String ip) {
        this.userPhoneIP = ip;
    }
}
