package org.mm3.serialdata;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import org.mm3.config.SerialPortConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: nbt86yz
 * Date: 7/7/15
 * Time: 3:50 PM

 */

public class SerialPortManager implements SerialPortEventListener {

    @Autowired
    protected SerialPortConfig serialPortConfig;


    public void connectToSerialPort(SerialPortConfig serialPortConfig){


    }

    public void connectToSerialPort() {


    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
