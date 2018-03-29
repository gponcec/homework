import java.net.*;
import java.io.*;
import java.util.*;

import java.io.IOException;
import java.net.InetAddress;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alien.enterpriseRFID.reader.*;
import com.alien.enterpriseRFID.tags.*;

public class Teste { 
        Boolean TemTag=false;        //Informa se existe tags no Banco
        ManIoTHueDriver m;           //Driver Lampadas Phillips Hue
        Storage s;                   //Driver Banco de Dados
		ControllerSwitchWemo cw;     //Driver Tomada Belkin Wemo

		Boolean STATUS_WEMO=false;

		int num_ligths=9;            //numero de lampadas 
		int num_iris=2;              //numero de sensores

		int[] anteriores = new int[]{100,100};


/*		public class Leitura_Tags extends Thread implements Runnable{	
		  public  AlienClass1Reader reader = new AlienClass1Reader();
		  public  String[] maniotTags = new String[3];
		
		  // Construction method
		  public Leitura_Tags() throws AlienReaderException{	
		    // Define the ID of the tag that will be used in ManIoT platform
		    maniotTags[0] = "1001 1002 1003 1004 1005 1006";
		    maniotTags[1] = "E200 3411 B802 0114 5234 2042";
		    maniotTags[2] = "E200 3412 DC03 0118 2815 3659";	
		    // To connect to a networked reader instead, use the following:
		    reader.setConnection("192.168.88.43", 23);
		    reader.setUsername("alien");
		    reader.setPassword("password");
		    reader.open();
		  }		
		  // Ask the reader to read tags. Return the tagList.
		  public Tag[] getTags() throws AlienReaderException, SQLException{	
		    Tag tagList[] = reader.getTagList();
		    printTagList(tagList);
		    return tagList;
		  }
		  // Print the tagList in the console
		  public void printTagList(Tag tagList[]){		
		    if(tagList == null){
		      System.out.println("\nNo Tags Found");
		    }
		    else{
		      System.out.println("\nTag(s) found:");
		      for(int i=0; i<tagList.length; i++){
			Tag tag = tagList[i];
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date renewTime = new Date(tag.getRenewTime());
			System.out.printf("ID: " + tag.getTagID() + "\tTime: " + dateFormat.format(renewTime) + "\n");				
		      }
		    }
		  }
		
		  // Print a tag in the console
		  public void printTag(Tag tag){
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date renewTime = new Date(tag.getRenewTime());
		    System.out.printf("ID: " + tag.getTagID() + "\tTime: " + dateFormat.format(renewTime) + "\t");
		  }

		  // Storage a tag in the table 'data' in ManIoT's database
		  public void tagToDB(int idResource, Tag tag) throws SQLException{
	//	    Date renewTime = new Date(tag.getRenewTime());
		    Storage.insertData(idResource, tag.getTagID());
		  }
		
		  // Search for the ManIoT's tags and storage them into the database
		  public int findManiotTags(Tag tagList[]) throws AlienReaderException, SQLException{
		    int numberOfTags = 0;
		    boolean isManiotTag = false;
		    if(tagList == null){
		      System.out.println("\nNo Tags Found");
		      return 0;
		    }
		    System.out.println("\nTag(s) found:");
		    for(int i=0; i<tagList.length; i++){
		      for(int j=0; j<maniotTags.length; j++){
			isManiotTag = false;
			if(maniotTags[j].equalsIgnoreCase(tagList[i].getTagID())){
			  numberOfTags++;
			  printTag(tagList[i]);
			  tagToDB(15, tagList[i]);
			  isManiotTag = true;
			  break;
			}
		      }	
		      if(!isManiotTag){
			printTag(tagList[i]);
			System.out.println("Not added in table 'data'.");
		      }
		    }
		    return numberOfTags;
		  }
		
		  // Identify if a tag was or was not readed in a certain time interval
		  public boolean isTagPresent(String idTag) throws SQLException{
		    int timeInterval = 120; // seconds
		    ResultSet rs = Storage.executeQuery("SELECT * FROM data WHERE time >= DATE_SUB(now(), INTERVAL " + timeInterval + " SECOND)");
		    String tag;
		    while(rs.next()){
		      tag = rs.getString("value");
		      System.out.println(tag);	
		      if(tag == idTag) return true;
		    }
		    return false;
		  } 

		  public void run(){
		    int readTime = 0;
		    try {
		      do {			
			if(findManiotTags(getTags()) == 0){
			  readTime = 5000;
			  if(STATUS_WEMO==true){
			    cw.SwitchOff();
			    STATUS_WEMO=false;
			  }
			}else{
			  readTime = 5000;
			  if(STATUS_WEMO==false){
			    cw.SwitchOn();
			    STATUS_WEMO=true;
			  }
			}
			Thread.sleep(readTime);
			System.out.println("OK");
		      }while(true);
		    }
		    catch(Exception e){
		      e.printStackTrace();
		    }
		  }
		}


*/		public class PresencaRede extends Thread implements Runnable{
		    
		    String addrguillermo = "150.164.10.53"; // gui
		    String addrguilherme = "150.164.10.80"; // guilherme
		    String addrguilhermepequeno = "192.168.88.37"; // guilherme pequeno
		    String addrjul = "150.164.10.113"; // julio
                    String addrnexus = "192.168.88.34";
		    String addr = addrguillermo;
		    int timeOut = 2000;
		    int delayTime = 2000;
                    int powerOnCounter = 0;
                    int powerOffCounter = 0;
		    int window = 5;
                    boolean wemoStatusOn = false;
		    int lastConnections[] = new int[window]; 
                    int counterConnections = 0;
                    int minConnections = 3;
		    
		    public void run() {
                        //System.out.println("entrou ao run");
		    
                         // Verify if the first (size=window) connections are false. IF false, then POWER ON
                         for (int i=0; i < lastConnections.length ; i++)
                         {
    		              lastConnections[i] = scanAddresses(addr, timeOut, delayTime);
    		              if (lastConnections[i]==1)
    			          counterConnections++;
    		              if (counterConnections==minConnections)
    		              {
                                  try{
                                     cw.SwitchOn();
    			             STATUS_WEMO = true;
                                     s.insertData(16,"entrou na sala");
    			             System.out.println("------------- "+addr + " - power ON");}
                                  catch(Exception e){}
    		              }
    		              //System.out.println(addr + " - " +lastConnections[i]);
                         }
        
    	                 if (areAllConnectionsFalse(lastConnections))
    	                 {
    		              powerOffCounter++;
    		              //System.out.println(addr + " - all falses");
    	                 }
    	                 
        
                         // Shift the connections vector and decide to power on/off the light
                         while (true) {
        	              // Shift the vector and update the last value 
        	              for (int i = 0; i < window-1; i++)
        	              {
        		          lastConnections[i] = lastConnections[i+1];
        		          System.out.print(lastConnections[i]+" - ");

            	                  if (STATUS_WEMO == false)
            	                  {
            		              if (lastConnections[i]==1)
            			          counterConnections++;
            	                  }
        	              }

        	              lastConnections[window-1] = scanAddresses(addr, timeOut, delayTime);
    		              System.out.print(lastConnections[window-1]+"\n");
  
                              // Decide to power on/off the light
        	              if (areAllConnectionsFalse(lastConnections))
        	              {
        		          powerOffCounter++;
        		         // System.out.println(addr + " - all falses");
        	              }
        	                      	
        	              if (STATUS_WEMO == false)
        	              {
        		          if (counterConnections==minConnections)
        		          {
                                      try{
                                          cw.SwitchOn();
        			          STATUS_WEMO = true;
                                          s.insertData(16, "entrou na sala");
        			          //powerOnCounter = 0;
        			          powerOffCounter = 0;
                                          counterConnections = 0;
        			          System.out.println("------------- "+addr + " - power ON");
                                      }
                                      catch(Exception e){}
        		          }
        	              }
        	              else
        	              {
        		          if (powerOffCounter == window)
        		          {
                                      try{
                                          cw.SwitchOff();
            		                  STATUS_WEMO = false;
                                          s.insertData(16, "saiu da sala");
            		                  //powerOnCounter = 0;
            		                  powerOffCounter = 0;
                                          counterConnections = 0;
            		                  System.out.println("------------- "+addr + " - power OFF");
                                      }
                                      catch(Exception e){}
        		          }
        	              }
        	
                          } 

                            		
		    }

		    public Integer scanAddresses(String addr, Integer timeOut, Integer delayTime){
			Integer wasFound = 0;
			try {
			    if (InetAddress.getByName(addr).isReachable(timeOut)){
				//System.out.println(addr + " found");
				wasFound = 1;
			    }
			    else{
				//System.out.println(addr + " not found");
				wasFound = 0;
			    }
			    delay(delayTime);	      

			} catch (IOException e) {
			    e.printStackTrace();
			}
			return wasFound;
		    }
	    
		    public void delay(Integer time){
			try {
			    Thread.sleep(time);
			} catch (Exception e) {
			    e.printStackTrace();
			}
		    }
	    
		        

		    // Validate if all the connections are false
                    public Boolean areAllConnectionsFalse(int[] connections){
    	                for(int i = 0; i < connections.length; i++) {
    	                    if(connections[i]==1)
                            {
                              
    	    	                return false;
                            }
    	                }

    	                return true;
                    }   
             }





        public class Atualizar extends Thread implements Runnable{
            volatile boolean flag = true;
            public void run() {
                while(flag){
                    //Insere Consumo
                    try{
                        Integer consumo=cw.ReadConsumptionStatus();
                        String c=consumo.toString();
                        s.insertData(14,c);
                    }
                    catch(Exception e){
                    }
                    //Atualiza o vetor anteriores com os valores dos iris
		    UpdateIris();
                    //Verifica se Existe tags recentes no banco
		    try{
                        //if(s.selectData(15)==1){
                          TemTag=true;
                       // }
                       // else{
                       //   TemTag=false;
                       // }
                    }
                    catch(Exception e){
                    }
                    if(!TemTag){
                        System.out.println("Nao tem tags, desligando as luzes !");
                        for(int i=1; i<=num_ligths; i++){
                            m.changeOnOff(i,false);
                        }
                    }
                    else{
                        for(int i=1; i<=num_ligths; i++) {
                            m.changeOnOff(i,true);
                            try {
                                m.changeBrightness(i,Convert_Brightness(anteriores[i-1]));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try{
                        Thread.sleep(5000);
                        System.out.println("AAAAAAAAAA\n\n\n\n");
                    } catch(Exception e){
                    }  
                }
            }
        }

        public void UpdateIris(){  //Atualizar os valores da luminosidade com os dados do iris
          for(int i=1; i<=num_iris; i++){
            int aux=-1;
            try{
              aux=s.selectData(i);
            }
            catch(Exception e){
            }
            if(aux!=-1){
              anteriores[i-1]=aux;
            }
          }
          for(int i=1; i<=num_iris; i++){
            System.out.println("L"+i+": " + anteriores[i-1]);
          }
        }

	public Teste() {
            //m=new  ManIoTHueDriver();
            s=new Storage();
            cw=new ControllerSwitchWemo();
	    /*
	    try{
              Leitura_Tags rfid = new Leitura_Tags();
	      Thread t1 = new Thread(rfid);
	      t1.start();
            }
	    catch(Exception e){
	    } 
	    */

	    try{
              PresencaRede p = new PresencaRede();
	      Thread t1 = new Thread(p);
	      t1.start();
            }
	    catch(Exception e){
	    }
   
            try{
              String[] args=new String[3];
              args[0]="-comm"; 
              args[1]="serial@/dev/ttyUSB1:iris"; 
              args[2]="-no-gui";
              SerialForwarder sf = new SerialForwarder(args);
              Thread t2=new Thread(sf);
              t2.start();
            }
            catch(Exception e){
            }
            //Atualizar a=new Atualizar();
            //a.start();
    	}

        public int Convert_Brightness(int lux){
            if(lux<200) {
                return 250;
            }
            if(lux>200 && lux<300) {
                return 200;
            }
            if(lux>300 && lux<400) {
                return 150;
            }
            if(lux>400 && lux<500) {
                return 125;
            }
            if(lux>500 && lux<600) {
                return 100;
            }
            if(lux>600 && lux<700) {
                return 75;
            }
            if(lux>700 && lux<800) {
                return 50;
            }
            if(lux>800){
                return 25;
            }
            return 0;
        }
	
	public static void main(String[] args) throws Exception {
            Teste e = new Teste();
	}
}
