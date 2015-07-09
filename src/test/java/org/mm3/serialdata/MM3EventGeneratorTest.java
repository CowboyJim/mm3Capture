package org.mm3.serialdata;

import jssc.SerialPortException;
import org.mm3.config.SerialPortConfig;
import org.mm3.data.MM3EventGenerator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by jim on 7/8/15.
 */

@Test
public class MM3EventGeneratorTest {


    protected MM3EventGenerator mm3EventGenerator;
    protected SerialPortConfig serialPortConfig;


    @BeforeTest
    public void setup() {
        mm3EventGenerator = new MM3EventGenerator();
        serialPortConfig = new SerialPortConfig();
    }

    @Test
    public void testConnectToSerialPort() {


        serialPortConfig.portID = "/dev/tty.usbserial";


        try {
            //     mm3EventGenerator.testConnection(serialPortConfig);
            mm3EventGenerator.connectToSerialPort(serialPortConfig);

        } catch (SerialPortException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}