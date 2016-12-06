package org.alancesar.arduino.async;

@FunctionalInterface
public interface ArduinoDataListener {

    void trigger(String data);
}
