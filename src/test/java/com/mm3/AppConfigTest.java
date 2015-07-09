package com.mm3;

import org.mm3.config.AppConfig;
import org.mm3.config.SerialPortConfig;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by CowboyJim on 7/8/15.
 */
public class AppConfigTest {


    protected AppConfig appConfig;

    @BeforeTest
    public void setup() {
        appConfig = new AppConfig();
    }

    @Test
    public void testComPortConfig() {

        String portId;

        appConfig = new AppConfig();
        SerialPortConfig serialPortConfig = appConfig.getSerialPortConfig();

        if (serialPortConfig.portID.contains("mac")) {
            assertEquals(serialPortConfig.portID, "/dev/tty.usbserial");
        } else if (serialPortConfig.portID.contains("win")) {
            assertEquals(serialPortConfig.portID, "COM1");
        }

        System.setProperty("com_port", "/tty_someport");
        appConfig = new AppConfig();
        assertEquals(appConfig.getSerialPortConfig().portID, "/tty_someport");

    }
}