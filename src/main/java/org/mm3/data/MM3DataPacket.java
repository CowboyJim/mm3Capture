package org.mm3.data;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 * Time: 2:45 PM
 */
public class MM3DataPacket {

    protected byte[] packetData;

    public MM3DataPacket(byte[] packetData) {
       this.packetData = packetData;
    }
}
