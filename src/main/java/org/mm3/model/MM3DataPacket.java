package org.mm3.model;

import org.mm3.util.ConversionUtils;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 */
public class MM3DataPacket {

    public static final int EKG_PACKET_SIZE = 39;
    private static int sequenceCount = 0;

    protected byte[] packetData;
    protected int[] intPacketData;

    public MM3DataPacket(byte[] packetData) {
        this.packetData = packetData;
        sequenceCount++;
        this.intPacketData = getAsIntArray(packetData);
    }

    /**
     * @return
     */
    public byte[] getByteArray() {
        return this.packetData;
    }

    /**
     * @return
     */
    public int[] getIntArray() {
        return this.intPacketData;
    }

    protected int[] getAsIntArray(byte[] packetData) {
        int[] data = new int[EKG_PACKET_SIZE];
        for (int x = 0; x < packetData.length; x++) {
            data[x] = ConversionUtils.byteToInt(packetData[x]);
        }
        return data;
    }

    public int[] getLeftChannelValues() {

        return null;
    }

    public int[] getRightChannelValues() {

        return null;
    }

    public static int getSequenceNum() {
        return sequenceCount;
    }

    public static void resetSequenceNum() {
        synchronized (MM3DataPacket.class) {
            sequenceCount = 0;
        }
    }
}
