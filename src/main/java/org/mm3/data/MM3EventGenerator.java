package org.mm3.data;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.mm3.config.SerialPortConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by CowboyJim on 7/8/15.
 */
public class MM3EventGenerator extends AbstractEventGenerator {

    protected Logger LOG = LoggerFactory.getLogger(MM3EventGenerator.class);


    protected SerialPort serialPort;

    @Autowired
    protected SerialPortConfig serialConfig;

    @Autowired
    protected ByteStreamParser byteStreamParser;

    @PostConstruct
    public void connectToSerialPort() throws SerialPortException {
        connectToSerialPort(serialConfig);
    }

    /**
     * @param serialConfig
     * @throws SerialPortException
     */
    public void connectToSerialPort(SerialPortConfig serialConfig) throws SerialPortException {
        serialPort = new SerialPort(serialConfig.portID);
        serialPort.openPort();
        serialPort.setParams(serialConfig.baudrate, serialConfig.databits, serialConfig.stopbits, serialConfig.parity,
                serialConfig.rts, serialConfig.dtr);

        serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
        serialPort.addEventListener(new SerialPortStreamReader());

    }

    /**
     *
     */
    public class SerialPortStreamReader implements SerialPortEventListener {

        /**
         * @param event
         */
        public void serialEvent(SerialPortEvent event) {

            //Process data if it is available
            if (event.isRXCHAR()) {
                try {
                    byteStreamParser.bytesReceived(serialPort.readBytes(event.getEventValue()));

                } catch (SerialPortException ex) {
                    LOG.error("SerialPortException", ex);
                }
            }
        }
    }


}
