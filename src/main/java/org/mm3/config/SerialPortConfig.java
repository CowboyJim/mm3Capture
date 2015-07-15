package org.mm3.config;

import jssc.SerialPort;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 */
public class SerialPortConfig {

    public String portID;
    public int baudrate = SerialPort.BAUDRATE_9600;
    public int parity = SerialPort.PARITY_NONE;
    public int stopbits = SerialPort.STOPBITS_1;
    public int databits = SerialPort.DATABITS_8;
    public boolean rts = false;
    public boolean dtr = true;

    public SerialPortConfig(Properties props) {
        portID = props.getProperty("portID");
        baudrate = Integer.valueOf(props.getProperty("baudrate"));
        parity = Integer.valueOf(props.getProperty("parity"));
        stopbits = Integer.valueOf(props.getProperty("stopbits"));
        databits = Integer.valueOf(props.getProperty("databits"));
    }

    public Properties asProperties() {
        Properties props = new Properties();
        props.setProperty("portID", portID);
        props.setProperty("baudrate", String.valueOf(baudrate));
        props.setProperty("databits", String.valueOf(databits));
        props.setProperty("parity", String.valueOf(parity));
        props.setProperty("stopbits", String.valueOf(stopbits));

        return props;

    }
}
