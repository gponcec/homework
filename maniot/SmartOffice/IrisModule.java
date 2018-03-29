import java.util.concurrent.TimeUnit;

class IrisModule implements Runnable{

        String[] params=new String[3];

	public void IrisModule(){
            params[0]="-comm";
            params[1]="serial@/dev/ttyUSB1:iris";
            params[2]="-no-gui";
	}


	public void run(){
	    try{
                SerialForwarder sf = new SerialForwarder(params);
                Thread t2=new Thread(sf);
                t2.start();
             }
                catch(Exception e){
             }
             try{
                TimeUnit.SECONDS.sleep(10);
             }
                catch(Exception e){
             }

            Oscilloscope me = new Oscilloscope();
            me.run();
        }
}
