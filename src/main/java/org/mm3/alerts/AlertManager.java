package org.mm3.alerts;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/21/15
 */
public class AlertManager {

    protected String alertDirectory = "alerts/";

    @Autowired
    private ApplicationContext context;

    public String getAlertDirectory() {
        return alertDirectory;
    }

    public void setAlertDirectory(String alertDirectory) {
        this.alertDirectory = alertDirectory;
    }

    public Map<String, GroovyObject> getAlertClasses() {
        return alertClasses;
    }

    protected Map<String, GroovyObject> alertClasses = new HashMap<>();

    @PostConstruct
    public void loadAlerts() throws IOException, IllegalAccessException, InstantiationException {

        final GroovyClassLoader classLoader = new GroovyClassLoader();
        Class alertClass;
        GroovyObject alertObj;

        Object[] argz = {};
        ClassPathResource cpr = new ClassPathResource(alertDirectory);
        System.out.println(cpr.exists());
        Resource resource = context.getResource(alertDirectory) ;
        System.out.println(resource.exists());
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }


        File dir = null; // = context.getResource("classpath:" + alertDirectory).getFile();
        if (dir != null) {
            File[] fileList = dir.listFiles();
            for (File file : fileList) {
                if (file.isFile()) {
                    alertClass = classLoader.parseClass(file);
                    alertObj = (GroovyObject) alertClass.newInstance();
                    alertClasses.put((String) alertObj.getProperty("name"), alertObj);
                }
            }
        }
    }
}
