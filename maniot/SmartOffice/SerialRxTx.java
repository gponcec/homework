import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;


public class SerialRxTx implements SerialPortEventListener, Runnable{
	SerialPort serialPort;
        /** Look for serial ports from the most common SOs. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;/** The input stream to the port */
	private OutputStream output;/** The output stream to the port */
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;/** Default connection timeout time. */
	private static final int DATA_RATE = 9600;/** Default bits per second for COM port. */
	
	private TreatData TD = new TreatData();/** Initializes data strip and treatment */
	private Storage S = new Storage(); /** Initializes database connection */


	public SerialRxTx(){
	

	}

	public void initialize() {
        // the next sets the connection configuration
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		CommPortIdentifier portId = null;
		//System.out.println("Checkpoint 1");
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		//System.out.println("Checkpoint 2");

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {	
		//System.out.println("Checkpoint 3");
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			//System.out.println(currPortId.getName());
			//System.out.println("Checkpoint 4");
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find connection port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				TD.setSerialReader(input.readLine());

                SensorsValues site = new SensorsValues();
                site.requestSensorsValues();

                int windowStatus = site.getDWStatusByDevice("Janela Daniel");
                int doorStatus = site.getDWStatusByDevice("Porta Daniel");
				S.addEnvironment(TD.getTemperature(), TD.getPressure(), TD.getHumidity(), TD.getNoise(), TD.getCo2(),windowStatus,doorStatus, 15);

				//System.out.println(TD.getTemperature() + " " + TD.getPressure() + " " + TD.getHumidity() + " " + TD.getNoise() + " " + TD.getCo2());
				//S.insertUser(TD.getFirst(), TD.getNumber(), TD.getLast());
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
	
	public void run(){
	    initialize();
	}
}//class end
