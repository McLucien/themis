import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

public class LED extends IS31FL3731 {
	
	//CONSTRUCTEUR
	
	public LED() throws Exception {
		
		super();
		I2CBus bus1 = I2CFactory.getInstance(I2CBus.BUS_1);
		I2CDevice IS31FL3731Device = bus1.getDevice((byte) 0x74);
		i2cDevice = IS31FL3731Device;
		
	}
	
	//ATTRIBUTS
	static int HIGH = 1;
	static int LOW = 0;
	int MS =1;
	
	protected int val;
	protected int state;
	
	
// 															METHODES
	
//Shutdowns LEDs
	static void resetLED(int row) throws IOException, InterruptedException, UnsupportedBusNumberException  {
		
		int i;
		for (i=0;i<8;i++) {
			
			switchLED(row,i,LOW);
			Thread.sleep(20);
			switchLED(row+1,i,LOW);
			Thread.sleep(20);
		}
	}
	
	//Allume plus ou moins de LED, de façon binary
	static void LedInDecBinary(int row, int inc,int val, int state) throws IOException, InterruptedException, UnsupportedBusNumberException  {

		if (state == 0){
			if (inc == 0){}
			else {		
				val = val | 7;
				if(val==0) { //All LEDs are turned off
					if(inc==-1) resetLED(row);
					else switchLED(row,0,HIGH);
				}
				else { 
					if (inc == -1) switchLED(row, val-1, LOW);
					else switchLED(row,val+1,HIGH);
				}
			}
		}
	}
	
	static void LedInDecPWM(int row, int col, int inc,int val, int state) throws IOException, InterruptedException, UnsupportedBusNumberException  {

		if (state == 0){
			
			val = val & 127;
			if(val==0) { //All LEDs are turned off
				if(inc==-1) resetLED(row);
				else setLEDpwm(row, col, val+2);
			}
			else { 
				if (inc == -1) setLEDpwm(row, col, val-2);
				else setLEDpwm(row, col, val+2);
			}
		}
	}
}