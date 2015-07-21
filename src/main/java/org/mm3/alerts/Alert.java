package org.mm3.alerts;

import org.mm3.data.MM3DataPacket;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/21/15
 */
public interface Alert {

    String getName();
    boolean isConditionMet(MM3DataPacket packet);

}
