package org.mm3.data;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Observable;

/**
 * Created by jim on 7/8/15.
 */
public class BaseEventGenerator extends Observable implements MM3PacketListener {

    @Autowired
    protected DataParser parser;

    public void setParser(DataParser parser) {
        this.parser = parser;
    }

    /**
     * @param packet
     */
    public void packetReceived(MM3DataPacket packet) {
        this.hasChanged();
        this.notifyObservers(packet);
    }

    @PostConstruct
    public void init(){
        parser.setPacketListener(this);
    }

}
