package org.mm3.alerts;

import org.mm3.model.MM3DataPacket;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/21/15
 */
public interface Alert {

    String getName();
    boolean isConditionMet(MM3DataPacket packet);

    String getDisplayColor();

}
