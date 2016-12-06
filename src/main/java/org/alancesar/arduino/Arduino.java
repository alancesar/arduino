package org.alancesar.arduino;

import java.io.IOException;

import org.alancesar.arduino.async.ArduinoDataListener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class Arduino {

    private final SerialPort serialPort;
    private final String serialPortName;

    public Arduino(String portDescription) {
        this.serialPortName = portDescription;
        serialPort = SerialPort.getCommPort(this.serialPortName);

        if (!serialPort.openPort()) {
            System.err.println(String.format("Error on connect %s port", this.serialPortName));
            return;
        }

        System.out.println(String.format("Connected on %s port", serialPortName));
    }

    public void close() {
        serialPort.closePort();
        serialPort.removeDataListener();
        System.out.println(String.format("Disconnected from %s port", serialPortName));
    }

    public void addDataListener(ArduinoDataListener listener) {
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {

                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {

                if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    if (serialPort.bytesAvailable() > 0) {
                        byte[] buffer = new byte[serialPort.bytesAvailable()];
                        serialPort.readBytes(buffer, buffer.length);
                        listener.trigger(new String(buffer));
                    }
                }
            }
        });
    }

    public void sendData(String data) {
        try {
            serialPort.getOutputStream().write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readData() {
        if (serialPort.bytesAvailable() > 0) {
            byte[] buffer = new byte[serialPort.bytesAvailable()];
            serialPort.readBytes(buffer, buffer.length);
            return new String(buffer);
        }

        return null;
    }

    public String getSerialPortName() {
        return serialPortName;
    }
}
