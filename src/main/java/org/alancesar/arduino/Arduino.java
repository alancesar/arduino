package org.alancesar.arduino;

import java.io.IOException;
import java.util.function.Consumer;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class Arduino {

    private SerialPort serialPort;
    private String serialPortName;
    private boolean connected;

    public Arduino() {
    }

    public Arduino(String serialPortName) {
        connect(serialPortName);
    }

    public synchronized void connect(String serialPortName) {
        if (connected) {
            System.err.println(String.format("Arduino is already connected on serial port %s", this.serialPortName));
            return;
        }

        this.serialPortName = serialPortName;
        serialPort = SerialPort.getCommPort(this.serialPortName);

        if (!serialPort.openPort()) {
            System.err.println(String.format("Error on connect %s port", this.serialPortName));
            return;
        }

        connected = true;
        System.out.println(String.format("Connected on %s port", serialPortName));
    }

    public synchronized void disconnect() {
        if (!connected) {
            return;
        }

        serialPort.closePort();
        serialPort.removeDataListener();
        connected = false;
        System.out.println(String.format("Disconnected from %s port", serialPortName));
    }

    public void addDataListener(Consumer<String> listener) {
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
                        listener.accept(new String(buffer));
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
    
    public boolean isConnected() {
        return connected;
    }
}
