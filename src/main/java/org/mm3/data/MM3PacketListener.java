package org.mm3.data;

import org.mm3.model.MM3DataPacket;

/**
 * Created with IntelliJ IDEA.
 * User: nbt86yz
 * Date: 7/13/15
 * Time: 9:47 AM
 */
public interface MM3PacketListener {

    void packetReceived(MM3DataPacket packet);
}
