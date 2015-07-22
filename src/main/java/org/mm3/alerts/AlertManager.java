package org.mm3.alerts;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

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

    public Map<String, GroovyObject> getAlertClasses() {
        return alertClasses;
    }

    protected Map<String, GroovyObject> alertClasses = new HashMap<>();

    @PostConstruct
    public void loadAlerts() throws IOException, IllegalAccessException, InstantiationException {

        final GroovyClassLoader classLoader = new GroovyClassLoader();
        Class alertClass;
        GroovyObject alertObj;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        Resource[] alertFiles = resolver.getResources("classpath:/**/*.alert");
        for (Resource resource : alertFiles) {
            File file = resource.getFile();
            if (file.isFile()) {
                alertClass = classLoader.parseClass(file);
                alertObj = (GroovyObject) alertClass.newInstance();
                alertClasses.put((String) alertObj.getProperty("name"), alertObj);
            }
        }
    }
}
