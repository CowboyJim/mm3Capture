package org.mm3.alerts;

import groovy.lang.GroovyObject;
import org.mm3.model.MM3DataPacket;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/22/15
 */
public class AlertManagerTest {

   // @Test
    public void testLoadAlerts() throws Exception {

        AlertManager am = new AlertManager();
        am.loadAlerts();

        Map<String, GroovyObject> classes = am.getAlertClasses();
        assertEquals(classes.size(), 1);
        Alert alert = (Alert) classes.get("TestAlert");
        assertNotNull(alert);

        boolean condition = alert.isConditionMet(new MM3DataPacket(new byte[]{0x01}));
        assertFalse(condition);


    }
}
