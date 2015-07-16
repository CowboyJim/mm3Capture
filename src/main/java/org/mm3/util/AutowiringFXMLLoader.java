package org.mm3.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

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

    public AutowiringFXMLLoader(String classpathUrl, Window owner, ApplicationContext context, StageStyle style) {
        super(style);

        this.context = context;
        InputStream fxmlStream = null;
        try {
            fxmlStream = context.getResource(classpathUrl).getInputStream();
            initOwner(owner);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new File("src/main/resources").toURI().toURL());

            Parent rootElement = (Parent) loader.load(fxmlStream);
            setScene(new Scene(rootElement));
            controller = loader.getController();
            recursiveWire(controller);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void recursiveWire(Object root) throws Exception {

        if (root == null) {
            return;
        }
        context.getAutowireCapableBeanFactory().autowireBean(root);
        context.getAutowireCapableBeanFactory().initializeBean(root, null);


        for (Field field : root.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(FXML.class) &&
                    !Node.class.isAssignableFrom(field.getType())) {
                // <== assume if not a Node, must be a controller
                recursiveWire(field.get(root));
            }
        }
    }
}
