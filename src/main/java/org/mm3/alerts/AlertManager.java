package org.mm3.alerts;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.mm3.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/21/15
 */
public class AlertManager {

    @Autowired
    protected AppConfig appConfig;
    protected Map<String, GroovyObject> alertClasses = new HashMap<>();

    public Map<String, GroovyObject> getAlertClasses() {
        return alertClasses;
    }

    @PostConstruct
    public void loadAlerts() throws IOException, IllegalAccessException, InstantiationException {

        final GroovyClassLoader classLoader = new GroovyClassLoader();
        Class alertClass;
        GroovyObject alertObj;

        File directory = new File(appConfig.getAlertDirectory());
        File[] alertFiles = directory.listFiles();

        for (File file : alertFiles) {
            if (file.isFile() && file.getName().contains("groovy")) {
                alertClass = classLoader.parseClass(file);
                alertObj = (GroovyObject) alertClass.newInstance();
                alertClasses.put((String) alertObj.getProperty("name"), alertObj);
            }
        }
    }
}
