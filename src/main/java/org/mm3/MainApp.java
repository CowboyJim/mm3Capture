package org.mm3;

import javafx.application.Application;
import javafx.stage.Stage;
import org.mm3.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig screens = context.getBean(AppConfig.class);
        screens.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Mind Mirror 3 Dynamic Visual Interface");
        screens.mainScreen().show();
    }
}
