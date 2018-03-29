//package restcommunicator;

public class RoomConfiguration{

   private int isConnectedWithServer;
   private int acuAirOn;
   private int acuTemperature;
   private int acuSpeed;
   private int acuSwingOn;
   private int acuLightOn;
   private float weatherRating;
   private float luminosityRating;
   private float noiseRating;
   private int isUserOrSystem;

    public RoomConfiguration()
    {
        this.isConnectedWithServer = 1;
        this.acuAirOn = 0;
        this.acuTemperature = 19;
        this.acuSpeed = 0;
        this.acuSwingOn = 0;
        this.acuLightOn = 0;
        this.weatherRating = 0;
        this.luminosityRating = 0;
        this.noiseRating = 0;
    }

   public int getIsConnectedWithServer(){
       return isConnectedWithServer;
   }

   public void setIsConnectedWithServer(int isConnectedWithServer){
       this.isConnectedWithServer = isConnectedWithServer;
   }

   public int getAcuAirOn(){
   	return acuAirOn;
   }
   public void setAcuAirOn(int airOn){
	this.acuAirOn = airOn;
   }

   public int getAcuTemperature(){
   	return acuTemperature;
   }
   public void setAcuTemperature(int temperature){
	this.acuTemperature = temperature;
   }

   public int getAcuSpeed(){
   	return acuSpeed;
   }
   public void setAcuSpeed(int speed){
	this.acuSpeed = speed;
   }

   public int getAcuSwingOn(){
   	return acuSwingOn;
   }
   public void setAcuSwingOn(int swingOn){
	this.acuSwingOn = swingOn;
   }

   public int getAcuLightOn(){
   	return acuLightOn;
   }
   public void setAcuLightOn(int lightOn){
	this.acuLightOn = lightOn;
   }


   public float getWeatherRating(){
   	return weatherRating;
   }
   public void setWeatherRating(float rating){
	this.weatherRating = rating;
   }

   public float getLuminosityRating(){
   	return luminosityRating;
   }
   public void setLuminosityRating(float rating){
	this.luminosityRating = rating;
   }

   public float getNoiseRating(){
   	return noiseRating;
   }
   public void setNoiseRating(float rating){
	this.noiseRating = rating;
   }

   @Override
   public String toString(){
	return "Configuration [isConnectedWithServer=" + isConnectedWithServer + 
            ", acuAirOn=" + acuAirOn + 
		    ", acuTemperature=" + acuTemperature + 
            ", acuSpeed=" + acuSpeed + 
            ", acuSwingOn=" + acuSwingOn + 
            ", acuLightOn=" + acuLightOn + 
            ", weatherRating=" + weatherRating + 
            ", luminosityRating=" + luminosityRating + 
            ", noiseRating=" + noiseRating +"]";
   }


}

