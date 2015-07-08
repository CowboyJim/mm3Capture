package org.mm3;


import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: nbt86yz
 * Date: 7/7/15
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpringFxmlLoader {
    private ApplicationContext context;

    public SpringFxmlLoader(ApplicationContext context)
    {
        this.context = context;
    }

    public Object load(String url, Class<?> controllerClass) throws IOException
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = controllerClass.getResourceAsStream(url);
            Object instance = context.getBean(controllerClass);
            FXMLLoader loader = new FXMLLoader();
            loader.getNamespace().put("controller", instance);
            return loader.load(fxmlStream);
        }
        finally
        {
            if (fxmlStream != null)
            {
                fxmlStream.close();
            }
        }
    }
}
