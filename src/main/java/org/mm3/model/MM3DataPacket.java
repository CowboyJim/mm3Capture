package org.mm3.model;

import org.mm3.util.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/7/15
 */
public class MM3DataPacket {

    public static final Map<String, Integer> INDEX_OF;
    public static final int EKG_PACKET_SIZE = 39;
    private static int sequenceCount = 0;

    static {
        INDEX_OF = new HashMap<>();
        INDEX_OF.put("0.75", 0);
        INDEX_OF.put("1.5", 1);
        INDEX_OF.put("3", 2);
        INDEX_OF.put("4.5", 3);
        INDEX_OF.put("6", 4);
        INDEX_OF.put("7.5", 5);
        INDEX_OF.put("9", 6);
        INDEX_OF.put("10.5", 7);
        INDEX_OF.put("12.5", 8);
        INDEX_OF.put("15", 9);
        INDEX_OF.put("19", 10);
        INDEX_OF.put("24", 11);
        INDEX_OF.put("30", 12);
        INDEX_OF.put("38", 13);
    }

    protected Logger LOG = LoggerFactory.getLogger(MM3DataPacket.class);
    protected byte[] packetData;
    protected int[] intPacketData;

    public MM3DataPacket(byte[] packetData) {
        this.packetData = packetData;
        sequenceCount++;
        this.intPacketData = getAsIntArray(packetData);
    }

    public static int getSequenceNum() {
        return sequenceCount;
    }

    public static void resetSequenceNum() {
        synchronized (MM3DataPacket.class) {
            sequenceCount = 0;
        }
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
        if (packetData.length <= EKG_PACKET_SIZE) {
            for (int x = 0; x < packetData.length; x++) {
                data[x] = ConversionUtils.byteToInt(packetData[x]);
            }
        }
        LOG.debug(Arrays.toString(data));

        return data;
    }

    public int[] getLeftChannelValues() {

        int channelData[] = new int[14];
        System.arraycopy(intPacketData, 10, channelData, 0, 14);
        return channelData;
    }

    public int[] getRightChannelValues() {

        int channelData[] = new int[14];
        System.arraycopy(intPacketData, 25, channelData, 0, 14);
        return channelData;
    }
}
