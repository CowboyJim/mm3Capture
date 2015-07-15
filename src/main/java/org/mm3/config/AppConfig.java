package org.mm3.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;
import org.mm3.data.FileBasedEventGenerator;
import org.mm3.data.MM3EventGenerator;
import org.mm3.data.MM3StreamParser;
import org.mm3.util.AutowiringFXMLLoader;
import org.mm3.util.CommonDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/14/15
 */
@Configuration
@Lazy
public class AppConfig {

    protected Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    private Stage primaryStage;

    public static final String PROPERTY_FILE_NAME = "mm3_ui.properties";

    @Autowired
    private ApplicationContext context;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showScreen(Parent screen) {
        primaryStage.setScene(new Scene(screen, 1100, 600));
        primaryStage.show();
    }

    protected Properties readProperties() {
        Properties savedProps = null;
        InputStream input = null;

        input = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
        if (input == null) {
            LOG.warn("Could not find properties file. Loading defaults");
            savedProps = getDefaultProperties();
        } else {

            savedProps = new Properties();
            try {
                savedProps.load(input);

            } catch (IOException e) {
                LOG.error("Error loading properties file. Loading defaults");
                savedProps = getDefaultProperties();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        LOG.error("Exception closing input stream", e);
                    }
                }
            }

        }
        return savedProps;
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
        props.setProperty("portID", portId);
        props.setProperty("baudrate", "9600");
        props.setProperty("databits", "8");
        props.setProperty("parity", "0");
        props.setProperty("stopbits", "1");

        return props;
    }

    @Bean
    public SerialPortConfig SerialPortConfig() {
        return new SerialPortConfig(readProperties());
    }

    @Bean
    public AutowiringFXMLLoader mainScreen() {
        return new AutowiringFXMLLoader("MainApp.fxml", primaryStage, context);
    }

    @Bean
    @Scope(value = "prototype")
    public MM3StreamParser MM3StreamParser() {
        return new MM3StreamParser();
    }

    @Bean
    public MM3EventGenerator MM3EventGenerator() {
        return new MM3EventGenerator();
    }

    @Bean
    public FileBasedEventGenerator FileBasedEventGenerator() {
        return new FileBasedEventGenerator();
    }

    @Bean
    public CommonDialogs CommonDialogs() {
        return new CommonDialogs();
    }
}
