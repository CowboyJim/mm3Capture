package org.mm3;

import javafx.application.Application;
import javafx.stage.Stage;
import org.mm3.config.SpringConfig;
import org.mm3.data.MM3EventGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp extends Application {

    protected AnnotationConfigApplicationContext context;

    protected Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            context
                    = new AnnotationConfigApplicationContext(SpringConfig.class);
            context.registerShutdownHook();

            SpringConfig screens = context.getBean(SpringConfig.class);
            screens.setPrimaryStage(primaryStage);
            screens.mainScreen().show();
        } catch (Exception e) {
            LOG.error("Uncaught application exception! Shutting down", e);
        } finally {
            MM3EventGenerator generator = context.getBean(MM3EventGenerator.class);
            if (generator != null) {
                LOG.info("Shutting down COM port if open");
                generator.shutdown();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (context != null) {
            context.close();
        }
        super.stop();
    }
}
