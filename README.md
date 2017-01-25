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
```java
String data = arduino.readData();
System.out.println(data);
```

##Send serial data
```java
arduino.send("Hello world");
```

##Serial event listener
Trigger an event every time serial send new data
```java
arduino.addDataListener(data -> System.out.println(data));
```

##Closing serial connection
```java
arduino.close();
```
