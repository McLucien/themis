
public class BarGraph extends LED{

	public BarGraph() throws Exception {
		
		super();
		
	}
	
	public static final int LED_MIN_VALUE = 0;
	public static final int LED_MAX_VALUE = 8;
	public static final int MIDI_MIN_VALUE = 0;
	public static final int MIDI_MAX_VALUE = 128;	
	
	private static int ledValue; // must be b/w 0 (no LED active) and 8
	private static int midiValue; // must be b/w 0 (no LED active) and 128
	private static int row;
	private static int incValue; // must be 1 or -1
	
	
	//Accessor
	
	//LED VALUE
	public static void setLedValue(int v) {
		if (v > LED_MAX_VALUE)
			throw new IllegalArgumentException(v + " greater than " + LED_MAX_VALUE);
		else if (v < LED_MIN_VALUE)
			throw new IllegalArgumentException(v + " lower than " + LED_MIN_VALUE);
		ledValue = v;
	}
	
	public static int getLedValue() {
		return ledValue;
	}
	
	//MIDI VALUE
	
	public static void setMidiValue(int v) {
		if (v > MIDI_MAX_VALUE)
			throw new IllegalArgumentException(v + " greater than " + MIDI_MAX_VALUE);
		else if (v < MIDI_MIN_VALUE)
			throw new IllegalArgumentException(v + " lower than " + MIDI_MIN_VALUE);
		midiValue = v;
	}
	
	public static int getMidiValue() {
		return midiValue;
		
	}
	
	
	public static int getRowAddress() {
		return registerOfStateLED(row);
		
	}
	
	//INCREMENTAION VALUE
	
	public static void setIncValue(int i) {
		if (i == 1 | i == -1 | i==0 )
			throw new IllegalArgumentException("inc value is wrong");
		incValue = i;
	}
	
	public static int getIncValue() {
		return incValue;
	}
	
	public static void main (String args[]) throws Exception {
		
		new BarGraph();
		int i;
		for (i=0;i<16;i++) resetLED(i); //reset all LED before starting
		
		
		//MODIFIED VALUES
		int led;
		int midi;
		int inc;
		
		while(true) {
			int old; //OLD VALUE TO COMPARE WITH
			
			//SWITCHING OFF/ON LED
			old = ledValue;
			setLedValue(led);
			if (old != ledValue)
				setIncValue(inc);
				LedInDecBinary(row, getIncValue(), getLedValue(), 0);
			
			
			//INCREASING INTENSITY
			old = midiValue;
			setMidiValue(midi);
			if (old != midiValue)
				setIncValue(inc);
				LedInDecPWM(row, getLedValue(), getIncValue(),getMidiValue(), 0);
			
			
			Thread.sleep(50);
			
			
		}
	}
			
}
