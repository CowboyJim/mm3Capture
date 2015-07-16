package org.mm3.data;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.mm3.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;

/**
 * Created by CowboyJim on 7/8/15.
 */
public class MM3EventGenerator extends BaseEventGenerator {

    @Autowired
    protected AppConfig appConfig;

    protected SerialPort serialPort;

    protected boolean connectedToSerial = false;

    public void connectToSerialPort() throws SerialPortException {
        connectToSerialPort(appConfig);
    }

    /**
     * @param serialConfig
     * @throws SerialPortException
     */
    public void connectToSerialPort(AppConfig serialConfig) throws SerialPortException {

        if (!connectedToSerial) {
            serialPort = new SerialPort(serialConfig.getPortID());
            serialPort.openPort();
            serialPort.setParams(serialConfig.getBaudrate(), serialConfig.getDatabits(), serialConfig.getStopbits(), serialConfig.getParity(),
                    serialConfig.isRts(), serialConfig.isDtr());

            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(new SerialPortStreamReader());
            connectedToSerial = true;
            LOG.info("Connected to serial port: " + serialConfig.getPortID());
        }
    }

    public void disconnectFromSerialPort() throws SerialPortException {
        serialPort.removeEventListener();
        if (serialPort.isOpened()) {
            serialPort.closePort();
        }
        serialPort = null;
        connectedToSerial = false;
        LOG.info("Disconnected from serial port: " + appConfig.getPortID());
    }

    @PreDestroy
    public void shutdown() {
        if (serialPort != null) {
            try {
                disconnectFromSerialPort();
            } catch (SerialPortException e) {
                LOG.error("SerialPortException", e);
            }
        }
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
                    parser.parseData(serialPort.readBytes(event.getEventValue()));

                } catch (SerialPortException ex) {
                    LOG.error("SerialPortException", ex);
                }
            }
        }
    }
}
