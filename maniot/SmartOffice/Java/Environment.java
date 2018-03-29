//package restcommunicator;

public class Environment{
    private int temperature;
    private int pressure;
    private int humidity;
    private int noise;
    private int airQuality;
    private int luminosity1;
    private int luminosity2;
    private String time;

    public Environment()
    {
        this.temperature = -1;
        this.pressure = -1;
        this.humidity = -1;
        this.noise = -1;
        this.airQuality = -1;
        this.luminosity1 = -1;
        this.luminosity2 = -1;
        this.time = "empty";
    }

    public int getTemperature(){
        return temperature;
    }
    public void setTemperature(int temperature){
        this.temperature = temperature;
    }

    public int getPressure(){
        return pressure;
    }

    public void setPressure(int pressure){
        this.pressure = pressure;
    }

    public int getHumidity(){
        return humidity;
    }

    public void setHumidity(int humidity){
        this.humidity = humidity;
    }

    public int getNoise(){
        return noise;
    }

    public void setNoise(int noise){
        this.noise = noise;
    }

    public int getAirQuality(){
        return airQuality;
    }

    public void setAirQuality(int airQuality){
        this.airQuality = airQuality;
    }

    public int getLuminosity1(){
        return luminosity1;
    }

    public void setLuminosity1(int luminosity1){
        this.luminosity1 = luminosity1;
    }

    public int getLuminosity2(){
        return luminosity2;
    }

    public void setLuminosity2(int luminosity2){
        this.luminosity2 = luminosity2;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

   @Override
   public String toString(){
	return "Environment [temperature =" + temperature +
            ", pressure =" + pressure +
            ", humidity =" + humidity +
            ", noise =" + noise +
            ", airQuality =" + airQuality +
            ", luminosity1 =" + luminosity1 +
            ", luminosity2 =" + luminosity2 +
            ", time =" + time +"]";
   }


}

