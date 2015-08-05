package org.mm3.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.mm3.view.MainController;
import org.mm3.view.NestedController;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/14/15
 */
public class AutowiringFXMLLoader extends Stage {

    protected final Object controller;
    private ApplicationContext context;

    public AutowiringFXMLLoader(String classpathUrl, Window owner, ApplicationContext context) {
        this(classpathUrl, owner, context, StageStyle.DECORATED);
    }

    public AutowiringFXMLLoader(String fxmlFileName, Window owner, ApplicationContext context, StageStyle style) {
        super(style);

        this.context = context;
        try {
            initOwner(owner);
            FXMLLoader loader = new FXMLLoader();

            URL fxmlUrl = this.getClass().getClassLoader().getResource(fxmlFileName);
            loader.setLocation(fxmlUrl);
            Parent rootElement = loader.load();

            setScene(new Scene(rootElement));
            controller = loader.getController();

            MainController main = null;
            if (controller instanceof MainController) {
                main = (MainController) controller;
            }

            recursiveWire(controller, main);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void recursiveWire(Object root, MainController main) throws Exception {

        if (root == null) {
            return;
        }
        context.getAutowireCapableBeanFactory().autowireBean(root);
        context.getAutowireCapableBeanFactory().initializeBean(root, null);
        if (main != null && root instanceof NestedController) {
            ((NestedController) root).setMainController(main);
        }

        for (Field field : root.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(FXML.class) &&
                    !Node.class.isAssignableFrom(field.getType())) {
                // <== assume if not a Node, must be a controller
                recursiveWire(field.get(root), main);
            }
        }
    }
}
