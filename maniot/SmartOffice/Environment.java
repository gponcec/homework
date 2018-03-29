public class Environment{
    private int envTemperature;
    private int envPressure;
    private int envHumidity;
    private int envNoise;
    private int envAirQuality;
    private int envLuminosity1;
    private int envLuminosity2;
    private int envOpenWindow;
    private int envOpenDoor;
    private String envTrackTime;

    public Environment()
    {
        this.envTemperature = -1;
        this.envPressure = -1;
        this.envHumidity = -1;
        this.envNoise = -1;
        this.envAirQuality = -1;
        this.envLuminosity1 = -1;
        this.envLuminosity2 = -1;
        this.envOpenWindow = -1;
        this.envOpenDoor = -1;
        this.envTrackTime = "empty";
    }

    public int getEnvTemperature(){
        return this.envTemperature;
    }
    public void setEnvTemperature(int envTemperature){
        this.envTemperature = envTemperature;
    }

    public int getEnvPressure(){
        return this.envPressure;
    }

    public void setEnvPressure(int envPressure){
        this.envPressure = envPressure;
    }

    public int getEnvHumidity(){
        return this.envHumidity;
    }

    public void setEnvHumidity(int envHumidity){
        this.envHumidity = envHumidity;
    }

    public int getEnvNoise(){
        return this.envNoise;
    }

    public void setEnvNoise(int envNoise){
        this.envNoise = envNoise;
    }

    public int getEnvAirQuality(){
        return this.envAirQuality;
    }

    public void setEnvAirQuality(int envAirQuality){
        this.envAirQuality = envAirQuality;
    }

    public int getEnvLuminosity1(){
        return this.envLuminosity1;
    }

    public void setEnvLuminosity1(int envLuminosity1){
        this.envLuminosity1 = envLuminosity1;
    }

    public int getEnvLuminosity2(){
        return this.envLuminosity2;
    }

    public void setEnvLuminosity2(int envLuminosity2){
        this.envLuminosity2 = envLuminosity2;
    }

    public int getEnvOpenWindow(){
        return this.envOpenWindow;
    }

    public void setEnvOpenWindow(int envOpenWindow){
        this.envOpenWindow = envOpenWindow;
    }

    public int getEnvOpenDoor(){
        return this.envOpenDoor;
    }

    public void setEnvOpenDoor(int envOpenDoor){
        this.envOpenDoor = envOpenDoor;
    }

    public String getEnvTrackTime(){
        return this.envTrackTime;
    }

    public void setEnvTrackTime(String envTrackTime){
        this.envTrackTime = envTrackTime;
    }

    @Override
    public String toString(){
	    return  "Environment [envTemperature =" + envTemperature +
                ", envPressure =" + envPressure +
                ", envHumidity =" + envHumidity +
                ", envNoise =" + envNoise +
                ", envAirQuality =" + envAirQuality +
                ", envLuminosity1 =" + envLuminosity1 +
                ", envLuminosity2 =" + envLuminosity2 +
                ", envOpenWindow =" + envOpenWindow +
                ", envOpenDoor =" + envOpenDoor +
                ", envTrackTime =" + envTrackTime +"]";
   }
}
