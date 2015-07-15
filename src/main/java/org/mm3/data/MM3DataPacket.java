package org.mm3.data;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 */
public class MM3DataPacket {

    private static int sequenceCount = 0;

    protected byte[] packetData;

    public MM3DataPacket(byte[] packetData) {
        this.packetData = packetData;
        sequenceCount++;
    }

    public byte[] getByteArray(){
        return packetData;
    }

    public static int getSequenceNum() {
        return sequenceCount;
    }
}
