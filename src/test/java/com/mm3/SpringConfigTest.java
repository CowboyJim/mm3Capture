package com.mm3;

import org.mm3.config.AppConfig;
import org.mm3.config.SpringConfig;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by CowboyJim on 7/8/15.
 */
public class SpringConfigTest {


    protected SpringConfig springConfig;

    @BeforeTest
    public void setup() {
        springConfig = new SpringConfig();
    }

    @Test
    public void testComPortConfig() {

        String portId;

        springConfig = new SpringConfig();
        AppConfig appConfig = springConfig.AppConfig();

        if (appConfig.getPortID().contains("mac")) {
            assertEquals(appConfig.getPortID(), "/dev/tty.usbserial");
        } else if (appConfig.getPortID().contains("win")) {
            assertEquals(appConfig.getPortID(), "COM1");
        }

//        System.setProperty("com_port", "/tty_someport");
//        springConfig = new SpringConfig();
//        assertEquals(springConfig.AppConfig().getPortID(), "/tty_someport");

    }
}