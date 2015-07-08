package org.mm3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mm3.capture.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

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


    public static void main(String[] args) {
        launch(args);
    }
}
