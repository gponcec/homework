import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class AirControl 
{
	//Get Variables
	public String get_Estado; // ligado - desligado
	public String get_Temp; // 17 ~ 26
	public String get_Swing; // 0 - 2
	public String get_Fan; // fraco, medio, forte
	
	//Set Variables
	public String set_Estado; //on - off
	public String set_Temp; // 17 ~ 26
	public String set_Fan; // number:0, number:1, number:2, number:3
	public String set_Swing; // on - off
	
	//number:0 -> forte
	//number:1 -> medio
	//number:2 -> fraco
	//number:3 -> auto
	
	public String Cookie;
	
	public AirControl(){
		get_Estado = "ligado";
		get_Temp = "21";
		get_Fan = "";
		get_Swing = "off";
		Login();
	}
	
	//Login function
	public void Login(){
		
		try {
			HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/login")
				  .header("origin", "https://painel.dcc.ufmg.br")
				  .header("upgrade-insecure-requests", "1")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cache-control", "no-cache")
				  .field("username", "guillermoponce")
				  .field("password", ".Amyponce0088")
				  .asString();
			
			Cookie = response.getHeaders().get("Set-Cookie").get(0);
		}catch(UnirestException e) {
			System.out.println("Login failed");
		}
		
	}
	
	//Commands to set Air Conditioner characteristics
	public void SetStatus(int id, String Status){
		try {
		HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/edit/" + Integer.toString(id))
				  .header("origin", "https://painel.dcc.ufmg.br")
				  .header("upgrade-insecure-requests", "1")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
				  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/edit/197")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cookie", Cookie)
				  .header("cache-control", "no-cache")
				  
				  //Comando que liga ou desliga o ar
				  .field("ligado", Status)
				  .asString();
		}catch(UnirestException e) {
			System.out.println("Failed to set Status");
		}
		
	}
	
	//
	public void SetTemp(int id, String Temp){
		try {
		HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/edit/" + Integer.toString(id))
				  .header("origin", "https://painel.dcc.ufmg.br")
				  .header("upgrade-insecure-requests", "1")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
				  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/edit/197")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cookie", Cookie)
				  .header("cache-control", "no-cache")
				  
				  //Comando que muda a temperatura do ar
				  .field("temp", Temp)
				  .asString();
		}catch(UnirestException e) {
			System.out.println("Failed to set temperature");
		}
		
	}
	
	public void SetFan(int id, String Fan) {
		
		if(Fan.equals("fraco"))
			Fan = "number:2";
		else if(Fan.equals("medio"))
			Fan = "number:1";
		else if(get_Fan.equals("forte"))
			Fan = "number:0";
		else
			Fan = "number:3";
		
		try {
			HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/edit/" + Integer.toString(id))
					  .header("origin", "https://painel.dcc.ufmg.br")
					  .header("upgrade-insecure-requests", "1")
					  .header("content-type", "application/x-www-form-urlencoded")
					  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
					  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
					  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					  .header("referer", "https://painel.dcc.ufmg.br/midea/edit/197")
					  .header("accept-encoding", "gzip, deflate, br")
					  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
					  .header("cookie", Cookie)
					  .header("cache-control", "no-cache")
					  
					  //Comando que liga ou desliga o ar
					  .field("fan", Fan)
					  .asString();
			}catch(UnirestException e) {
				System.out.println("Failed to set Status");
			}
		
	}
	
	public void SetSwing(int id, String Swing){
		try {
		HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/edit/" + Integer.toString(id))
				  .header("origin", "https://painel.dcc.ufmg.br")
				  .header("upgrade-insecure-requests", "1")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
				  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/edit/197")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cookie", Cookie)
				  .header("cache-control", "no-cache")
				  
				  //Comando que liga ou desliga o swing
				  .field("swing", Swing)
				  .asString();
		}catch(UnirestException e) {
			System.out.println("Failed to Set Swing");
		}
	}
	
	public void SetAll(int id, String Status, String Temp, String Swing, String Fan){
		GetParams(id);
		TreatString();
		
		/*if(Fan.equals("fraco"))
			Fan = "number:2";
		else if(Fan.equals("medio"))
			Fan = "number:1";
		else if(get_Fan.equals("forte"))
			Fan = "number:0";
		else
			Fan = "number:3";
		*/
		
		try {
		HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/edit/" + Integer.toString(id))
				  .header("origin", "https://painel.dcc.ufmg.br")
				  .header("upgrade-insecure-requests", "1")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
				  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/edit/197")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cookie", Cookie)
				  .header("cache-control", "no-cache")
				  
				  //Comandsos de controle do ar condicionado
				  .field("ligado", Status)
				  .field("temp", Temp)
				  .field("swing", Swing)
				  .field("fan", Fan)
				  .asString();
		}catch(UnirestException e) {
			System.out.println("Failed to set all");
		}
		
	}
	
	//Commands to get Air Conditioner actual characteristics
	public void GetParams(int id){
		JSONObject device;
		List<String> params = new ArrayList<String>();
		
		try {
		HttpResponse<JsonNode> response = Unirest.get("https://painel.dcc.ufmg.br/midea/detail/" + Integer.toString(id))
				  .header("accept", "application/json, text/plain, */*")
				  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cookie", "session=.eJwlzs1ugzAQBOBXqfacQzHhgpSbSQXSLiIysdaXKCFEscE98KMER3n30vY6I30zLzjdhna8QzoNc7uBk71C-oKPC6SA4epZ3S3r6mlcLUhzxK6wqDHhcOgo5LGRhTWSY1Tdwp4fpTz2pHEhmT9ZUP-bG5fH5DGw3_eoq3h1_nvXJOQLt9qfFJoteVr3ssDh2KPMEqPwUaomIrl6X7UoVZ2U2lh0nUCx74zKIhL1QiLbwXsD89gOf_9hmQf7be00zv4M7x8c6EyD.DPiMAA.mPVHlsFOK9PP0VA8LGsaki5qRZg")
				  .header("cache-control", "no-cache")
				  .asJson();
		
		device = (JSONObject) response.getBody().getObject().get("device");
		get_Estado = (String) device.get("estado");
		get_Temp = device.get("set_temp").toString();
		get_Swing = (String) device.get("swing").toString();
		get_Fan = (String) device.get("fan");
		}catch(UnirestException e) {
			System.out.println("Failed to get params");
		}
	}
	
	//Trata as variaveis lidas e as transforma nas formas corretas de comando
	public void TreatString() {
		//Treat status
		if(get_Estado.equals("ligado"))
			set_Estado = "on";
		else
			set_Estado = "off";
		
		//Treat Swing
		if(get_Swing.equals("2"))
			set_Swing = "on";
		else
			set_Swing = "off";
		
		//Treat Fan
		if(get_Fan.equals("fraco"))
			set_Fan = "number:2";
		else if(get_Fan.equals("medio"))
			set_Fan = "number:1";
		else if(get_Fan.equals("forte"))
			set_Fan = "number:0";
		else
			set_Fan = "number:3";
	}
	
	public String GetEstado() {
		return set_Estado;
	}
	
	public String GetTemp() {
		return set_Temp;
	}
	
	public String GetSwing() {
		return set_Swing;
	}
	
	public String GetFan() {
		return set_Fan;
	}
	
	public String GetCookie() {
		return Cookie;
	}
	
	//Ar do daniel = 147
	//Ar das salas = 197 e 196(nao funciona)
	public static void main( String[] args ) throws UnirestException, InterruptedException
    {
        AirControl Air = new AirControl();
        Air.SetAll(197,"on","22","off","fraco");
        Air.GetParams(197);
        //number:3 -> auto
        //number:2 -> fraco
        //number:1 -> medio
        //number:0 -> forte
    }
}
