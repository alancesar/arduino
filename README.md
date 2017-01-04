# arduino-api
Arduino Java Serial Communication API

##Initializing
```Java
// Mac OS X     "/dev/tty.usbmodem1421"
// Raspberry Pi "/dev/ttyACM0"
// Linux        "/dev/ttyUSB0"
// Windows      "COM3"
String serialPortName = ...;
Arduino arduino = new Arduino(serialPortName);
```

##Read serial data
```Java
String data = arduino.readData();
System.out.println(data);
```

##Send serial data
```
arduino.send("Hello world");
```

##Serial event listener
Trigger event every time serial send new data
```Java
// Java 8 lambda
arduino.addDataListener((data) -> System.out.println(data));

// Anonymous class
arduino.addDataListener(new ArduinoDataListener() {
  @Override
  public void trigger(String data) {
    System.out.println(data);
  }
});
```

##Closing serial connection
```Java
arduino.close();
```
