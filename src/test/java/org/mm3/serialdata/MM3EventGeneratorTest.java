package org.mm3.serialdata;

import jssc.SerialPortException;
import org.mm3.config.SerialPortConfig;
import org.mm3.data.MM3DataPacket;
import org.mm3.data.MM3EventGenerator;
import org.mm3.data.MM3PacketListener;
import org.mm3.data.MM3StreamParser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by jim on 7/8/15.
 */

@Test
public class MM3EventGeneratorTest implements MM3PacketListener {


    protected MM3EventGenerator mm3EventGenerator;
    protected SerialPortConfig serialPortConfig;
    MM3StreamParser parser;

    @BeforeTest
    public void setup() {

        parser = new MM3StreamParser();
        parser.setPacketListener(this);
        mm3EventGenerator = new MM3EventGenerator(parser);
        serialPortConfig = new SerialPortConfig();
    }

    @Test(enabled = false)
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

    @Override
    public void packetReceived(MM3DataPacket packet) {

    }
}