package org.mm3.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/14/15
 */
@Configuration
@Lazy
public class SpringConfig {

    protected Logger LOG = LoggerFactory.getLogger(SpringConfig.class);

    private Stage primaryStage;

    @Autowired
    private ApplicationContext context;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showScreen(Parent screen) {
        primaryStage.setTitle("Mind Mirror 3 Dynamic Visual Interface");
        primaryStage.setScene(new Scene(screen, 1100, 600));
        primaryStage.show();
    }


    @Bean
    public AppConfig AppConfig() {
        return new AppConfig();
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
