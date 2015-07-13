package org.mm3.config;

import javafx.fxml.FXMLLoader;
import jssc.SerialPort;
import org.mm3.SpringFxmlLoader;
import org.mm3.view.CaptureTableViewController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 * Time: 3:12 PM
 */

@Configuration
@ComponentScan(basePackages = "org.mm3.capture")
public class AppConfig {

    @Bean
    public SerialPortConfig getSerialPortConfig() {

        String portId = System.getProperty("com_port");

        if (portId == null) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                portId = "COM1";
            } else if (os.contains("mac")) {
                portId = "/dev/tty.usbserial";
            } else if (os.contains("nix")) {
                portId = "/dev/ttyS0";
            }

        }

        SerialPortConfig config = new SerialPortConfig();

        config.portID = portId;
        config.baudrate = SerialPort.BAUDRATE_9600;
        config.databits = SerialPort.DATABITS_8;
        config.parity = SerialPort.PARITY_NONE;
        config.stopbits = SerialPort.STOPBITS_1;

        return config;
    }

    @Bean
    public CaptureTableViewController getCaptureTableViewController() {
        return new CaptureTableViewController();
    }

    @Bean
    public SpringFxmlLoader getSpringFxmlLoader() {
        return new SpringFxmlLoader();
    }

    @Bean
    @Scope(value = "prototype")
    public FXMLLoader getFXMLLoader(){
        return new FXMLLoader();
    }

}
