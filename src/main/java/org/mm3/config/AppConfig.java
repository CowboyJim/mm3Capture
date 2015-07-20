package org.mm3.config;

import javafx.beans.property.SimpleStringProperty;
import jssc.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 * <p/>
 * Defaults:
 * public int baudrate = SerialPort.BAUDRATE_9600;
 * public int parity = SerialPort.PARITY_NONE;
 * public int stopbits = SerialPort.STOPBITS_1;
 * public int databits = SerialPort.DATABITS_8;
 */
public class AppConfig {

    public static final String PROPERTY_FILE_NAME = "./mm3_ui.properties";
    protected Logger LOG = LoggerFactory.getLogger(SpringConfig.class);
    protected String portID;
    protected int baudrate = SerialPort.BAUDRATE_9600;
    protected int parity = SerialPort.PARITY_NONE;
    protected int stopbits = SerialPort.STOPBITS_1;
    protected int databits = SerialPort.DATABITS_8;
    protected boolean rts = false;
    protected boolean dtr = true;
    protected String defaultDirectory = "./capture";
    protected Properties props;

    protected SimpleStringProperty comPort = new SimpleStringProperty();

    public String getComPort() {
        return comPort.get();
    }

    public SimpleStringProperty comPortProperty() {
        return comPort;
    }

    public void setComPort(String comPort) {
        this.comPort.set(comPort);
    }

    public AppConfig() {
        props = readProperties();
        setPortID(props.getProperty("portID"));
        baudrate = Integer.valueOf(props.getProperty("baudrate"));
        parity = Integer.valueOf(props.getProperty("parity"));
        stopbits = Integer.valueOf(props.getProperty("stopbits"));
        databits = Integer.valueOf(props.getProperty("databits"));
    }

    public String getPortID() {
        return portID;
    }

    public void setPortID(String portID) {
        setComPort(portID);
        this.portID = portID;
    }

    public int getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(int baudrate) {
        this.baudrate = baudrate;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public int getStopbits() {
        return stopbits;
    }

    public void setStopbits(int stopbits) {
        this.stopbits = stopbits;
    }

    public int getDatabits() {
        return databits;
    }

    public void setDatabits(int databits) {
        this.databits = databits;
    }

    public boolean isRts() {
        return rts;
    }

    public void setRts(boolean rts) {
        this.rts = rts;
    }

    public boolean isDtr() {
        return dtr;
    }

    public void setDtr(boolean dtr) {
        this.dtr = dtr;
    }

    public String getDefaultDirectory() {
        return defaultDirectory;
    }

    public void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    protected Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("portID", portID);
        props.setProperty("baudrate", String.valueOf(baudrate));
        props.setProperty("databits", String.valueOf(databits));
        props.setProperty("parity", String.valueOf(parity));
        props.setProperty("stopbits", String.valueOf(stopbits));
        props.setProperty("defaultDirectory", defaultDirectory);

        return props;
    }

    protected void setProperties() {

        setPortID(props.getProperty("portID"));
        baudrate = Integer.valueOf(props.getProperty("baudrate"));
        parity = Integer.valueOf(props.getProperty("parity"));
        stopbits = Integer.valueOf(props.getProperty("stopbits"));
        databits = Integer.valueOf(props.getProperty("databits"));
        defaultDirectory = props.getProperty("defaultDirectory");

    }

    public void saveProperties() {
        Properties propsOut = getProperties();
        OutputStream output = null;
        try {
            output = new FileOutputStream(PROPERTY_FILE_NAME);

            // save properties to project root folder
            propsOut.store(output, null);
        } catch (IOException io) {
            LOG.error("Could not store application properties", io);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    LOG.error("Exception closing stream", e);
                }
            }
        }
    }

    protected Properties readProperties() {

        FileInputStream input = null;

        try {
            input = new FileInputStream(new File(PROPERTY_FILE_NAME));
            props = new Properties();
            props.load(input);

        } catch (FileNotFoundException e) {
            LOG.warn("Could not find properties file. Loading defaults");
            props = getDefaultProperties();
            saveProperties();
        } catch (IOException e) {
            LOG.error("Error loading properties file. Loading defaults");
            props = getDefaultProperties();

        } finally {
            setProperties();

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.error("Exception closing input stream", e);
                }
            }
        }
        return props;
    }

    protected Properties getDefaultProperties() {
        Properties props = new Properties();
        String portId = null;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            portId = "COM1";
        } else if (os.contains("mac")) {
            portId = "/dev/tty.usbserial";
        } else if (os.contains("nix")) {
            portId = "/dev/ttyS0";
        }
        this.portID = portId;
        props.setProperty("portID", portId);
        props.setProperty("baudrate", "9600");
        props.setProperty("databits", "8");
        props.setProperty("parity", "0");
        props.setProperty("stopbits", "1");
        props.setProperty("defaultDirectory", "./capture");

        return props;
    }
}
