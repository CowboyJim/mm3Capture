package org.mm3;

import javafx.application.Application;
import javafx.stage.Stage;
import org.mm3.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp extends Application {

    protected AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        context
                = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.registerShutdownHook();

        SpringConfig screens = context.getBean(SpringConfig.class);
        screens.setPrimaryStage(primaryStage);
        screens.mainScreen().show();


    }

    @Override
    public void stop() throws Exception {
        if (context != null) {
            context.close();
        }
        super.stop();
    }
}
