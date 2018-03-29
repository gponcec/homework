class RoomConfiguration{

   private int isConnectedWithServer;
   private int acuAirOn;
   private int acuTemperature;
   private int acuSpeed;
   private int acuSwingOn;
   private int roomLightOn;
   private int roomOpenShade;
   private int userWeatherRating;
   private int userLuminosityRating;
   private int userNoiseRating;
   private int isUserOrSystem;
   private String appUser;

    public RoomConfiguration()
    {
        this.isConnectedWithServer = 1;
        this.acuAirOn = 0;
        this.acuTemperature = 19;
        this.acuSpeed = 0;
        this.acuSwingOn = 0;
        this.roomLightOn = 0;
        this.roomOpenShade = 0;
        this.userWeatherRating = 0;
        this.userLuminosityRating = 0;
        this.userNoiseRating = 0;
        this.isUserOrSystem = 0; // 0 for machine, 1 for user NEW 
        this.appUser = "none";
    }

    public int getIsConnectedWithServer(){
        return this.isConnectedWithServer;
    }

    public void setIsConnectedWithServer(int isConnectedWithServer){
        this.isConnectedWithServer = isConnectedWithServer;
    }

    public int getAcuAirOn(){
   	    return this.acuAirOn;
    }

    public void setAcuAirOn(int airOn){
	    this.acuAirOn = airOn;
    }

    public int getAcuTemperature(){
   	    return this.acuTemperature;
    }

    public void setAcuTemperature(int temperature){
	    this.acuTemperature = temperature;
    }

    public int getAcuSpeed(){
        return this.acuSpeed;
    }

    public void setAcuSpeed(int speed){
	    this.acuSpeed = speed;
    }

    public int getAcuSwingOn(){
   	    return this.acuSwingOn;
    }

    public void setAcuSwingOn(int swingOn){
	    this.acuSwingOn = swingOn;
    }

    public int getRoomLightOn(){
   	    return this.roomLightOn;
    }

    public void setRoomLightOn(int lightOn){
        this.roomLightOn = lightOn;
    }

    public int getRoomOpenShade(){
        return this.roomOpenShade;
    }

    public void setRoomOpenShade(int openShade){
        this.roomOpenShade = openShade;
    }

    public int getUserWeatherRating(){
   	    return this.userWeatherRating;
    }

    public void setUserWeatherRating(int rating){
	    this.userWeatherRating = rating;
    }

    public int getUserLuminosityRating(){
   	    return this.userLuminosityRating;
    }

    public void setUserLuminosityRating(int rating){
	    this.userLuminosityRating = rating;
    }

    public int getUserNoiseRating(){
   	    return this.userNoiseRating;
    }

    public void setUserNoiseRating(int rating){
	    this.userNoiseRating = rating;
    }

    public int getIsUserOrSystem(){
        return this.isUserOrSystem;
    }

    public void setIsUserOrSystem(int isUserOrSystem){
        this.isUserOrSystem = isUserOrSystem;
    }

    public String getAppUser(){
        return this.appUser;
    }

    public void setAppUser(String appUser){
        this.appUser = appUser;
    }

    @Override
    public String toString(){
	    return  "Configuration [isConnectedWithServer=" + isConnectedWithServer + 
                ", acuAirOn=" + acuAirOn + 
		        ", acuTemperature=" + acuTemperature + 
                ", acuSpeed=" + acuSpeed + 
                ", acuSwingOn=" + acuSwingOn + 
                ", roomLightOn=" + roomLightOn + 
                ", roomOpenShade=" + roomOpenShade + 
                ", userWeatherRating=" + userWeatherRating + 
                ", userLuminosityRating=" + userLuminosityRating + 
                ", userNoiseRating=" + userNoiseRating + 
                ", isUserOrSystem=" + isUserOrSystem +
                ", appUser=" + appUser +"]";
   }
}



