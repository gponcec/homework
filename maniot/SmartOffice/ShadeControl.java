//===================================================================================================
//Shade control module
//Implements every possible control for the shades in Java
//Developer: guilhermejp4@gmail.com
//Created: 31/01/2018
//===================================================================================================

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShadeControl {	
	private String mac;
	private String scriptExecution;
	
	//Constructor - DONE
	public ShadeControl(){
		mac = "D4:0C:1A:93:20:11";//Set shades mac adress		
		scriptExecution = "python control.py -t";//Set default execution script
	}
//===================================================================================================
	//Close shade - DONE
	public boolean closeShade() {
		String command = "-c move_down";
		
		try {
			Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
			System.out.println("shade is being closed");
		}
		catch(Exception ex) {
			System.out.println("Unable to close shade");
			return false;
		}//end of catch
		
		return true;
		
	}//end of closeShade
//===================================================================================================	
	//Open shade - DONE
	public boolean openShade() {
		String command = "-c move_up";
		
		try {
			Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
			System.out.println("shade is being opened");
		}
		catch(Exception ex) {
			System.out.println("Unable to open shade");
			return false;
		}//end of catch
		
		return true;
	}//end of openShade
//===================================================================================================
	//Setshade Position - DONE
	public boolean setShadePosition(int position) {
		String command = "-c move_target -a";
		
		if (position >= 0 && position <= 100) {
			try {
				Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command + " " + position);
				System.out.println("Shade moving.");		
			}
			catch(Exception ex) {
				System.out.println("Unable to move shade.");
				return false;
			}//end of catch
		}//end of if
		else {
			return false;
		}//end of else
		
		return true;
		}//end of setShadePosition
//===================================================================================================
	//Get shade Position - Partially done
	public int getShadePosition() {
		String command = "-c get_position";
		String s = null;
		
		try {
			Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((s=br.readLine())!=null)
			    {
			           System.out.println(s);
			    }
		}
		catch(Exception ex) {
			System.out.println("Unable to close open shade");
			return -1;//default return for failed retrieves
		}//end of catch
		
		return 100;//substitute by correct variable
	}//end of getshadePosition
//===================================================================================================
	//GetShade Battery
	public int getShadeBattery(){
		String command = "-c get_battery";
		String s = null;
		
		try {
			Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((s=br.readLine())!=null)
			    {
			           System.out.println(s);
			    }
		}
		catch(Exception ex) {
			System.out.println("Unable to close open shade");
			return -1;
		}//end of catch
		
		return 100;//substitute by correct variable
	}//End of getshadeBattery

  public void Test() {
                System.out.println("starting testing shade...");
                String command = "python test.py";
                System.out.println(getShadeBattery());
                System.out.println(getShadePosition());
                setShadePosition(0);
                /*
                try {
                        Process proc = Runtime.getRuntime().exec(command);
                        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

                        String s = null;

                        while ((s=br.readLine())!=null)
                            {
                                   System.out.println(s);
                                   System.out.println(getShadeBattery());
                            }
                }catch(Exception ex) {
                        System.out.println("Failed");
                }*/
                System.out.println("test finished!");
        }




}//End of class
//===================================================================================================
/*
	public void Test() {
		String command = "python test.py";
				
		try {
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
			String s = null;

			while ((s=br.readLine())!=null)
			    {
			           System.out.println(s);
			    }
		}catch(Exception ex) {
			System.out.println("Failed");
		}	
	}
*/
