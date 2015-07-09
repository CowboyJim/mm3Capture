package org.mm3.data;

import java.util.Observable;

/**
 * Created by jim on 7/8/15.
 */
public abstract class AbstractEventGenerator extends Observable implements PacketReceivedCallback {

    /**
     * @param packet
     */
    public void packetReceived(MM3DataPacket packet) {
        this.hasChanged();
        this.notifyObservers(packet);
    }


}
