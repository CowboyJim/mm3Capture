package org.mm3.data;

import java.util.Observable;

/**
 * Created by jim on 7/8/15.
 */
public class BaseEventGenerator extends Observable implements MM3PacketListener {

   protected DataParser parser;

    public BaseEventGenerator(DataParser parser) {
        this.parser = parser;
        parser.setPacketListener(this);
    }

    /**
     * @param packet
     */
    public void packetReceived(MM3DataPacket packet) {
        this.hasChanged();
        this.notifyObservers(packet);
    }

}
