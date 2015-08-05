package org.mm3;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 */
public class SpringFxmlLoader implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public Object load(String url, Class<?> controllerClass) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = context.getResource("classpath:" + url).getInputStream();
            Object controller = context.getBean(controllerClass);
            FXMLLoader loader = context.getBean(FXMLLoader.class);
            loader.setController(controller);
            Object fxml =  loader.load(fxmlStream);
            ((Initializable)controller).initialize(null,null);
            return fxml;

        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

}
