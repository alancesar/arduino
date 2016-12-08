package org.alancesar.arduino;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SerialTest {
	
	Arduino arduino;
	
	@Before
	public void init() throws InterruptedException {
		 arduino = new Arduino("COM11");
		 Thread.sleep(1000);
	}
	
	@After
	public void finalize() throws InterruptedException {
		Thread.sleep(1000);
		arduino.disconnect();
	}
	
	@Test
	public void sendData() {
		System.out.println("Sending data...");
		arduino.sendData("Test");
	}
	
	@Test
	public void read() {
		System.out.println("Read: " + arduino.readData());
	}
	
	@Test
	public void listener() {
		arduino.addDataListener((data) -> System.out.println("Listener: " + data));
	}
}
