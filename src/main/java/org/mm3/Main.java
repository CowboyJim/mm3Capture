package org.mm3;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mm3.capture.Controller;
import org.mm3.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        AnnotationConfigApplicationContext context
               = new AnnotationConfigApplicationContext(AppConfig.class);
        SpringFxmlLoader loader = new SpringFxmlLoader(context);

        Parent root = (Parent) loader.load("/mm3_ui_main.fxml", Controller.class);
        primaryStage.setTitle("Mind Mirror 3 Dynamic Visual Interface");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
