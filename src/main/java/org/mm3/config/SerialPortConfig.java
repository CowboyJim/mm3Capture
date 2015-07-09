package org.mm3.config;

import jssc.SerialPort;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 * Time: 3:52 PM
 */
public class SerialPortConfig {
    public String portID;
    public int baudrate = SerialPort.BAUDRATE_9600;
    public int parity = SerialPort.PARITY_NONE;
    public int stopbits = SerialPort.STOPBITS_1;
    public int databits = SerialPort.DATABITS_8;
    public boolean rts = false;
    public boolean dtr = true;

}
