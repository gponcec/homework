
public class TreatData {
	private String temperature;
	private String humidity;
	private String co2;
	private String noise;
	private String pressure;
	
	//Test variables
	private String First;
	private String Number;
	private String Last;
	
	private String serialReader;
	
	
	private void interpretSerialLine() {
		//Separate strings delimited by space
		//Temperatura-Pressao-Umidade-Ruido-CO2
		String aux[] = serialReader.split(" ");
		if(aux.length == 5) {
			temperature = aux[0];
			pressure = aux[1];
			humidity = aux[2];
			noise = aux[3];
			co2 = aux[4];
		}
		
	}
	
	
	//Set commands
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public void setCo2(String co2) {
		this.co2 = co2;
	}
	public void setNoise(String noise) {
		this.noise = noise;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public void setSerialReader(String serialReader) {
		this.serialReader = serialReader;//Read recieved serial line
		this.interpretSerialLine();//Treat recieved line
	}
	
	//==============================================
	//Set test variables
	public void setFirst(String First) {
		this.First = First;
	}
	public void setNumber(String Number) {
		this.Number = Number;
	}
	public void setLast(String Last) {
		this.Last = Last;
	}
	//==============================================
	
	//Get variables
	public int getTemperature() {
		return Integer.parseInt(temperature);
	}
	public int getHumidity() {
		return Integer.parseInt(humidity);
	}
	public int getCo2() {
		return Integer.parseInt(co2);
	}
	public int getNoise() {
		return Integer.parseInt(noise);
	}
	public int getPressure() {
		return Integer.parseInt(pressure);
	}
	public String getSerialReader() {
		return serialReader;
	}
	
	//==============================================
	//Get test variables
	public String getFirst() {
		return First;
	}
	public String getNumber() {
		return Number;
	}
	public String getLast() {
		return Last;
	}
	//==============================================

}
