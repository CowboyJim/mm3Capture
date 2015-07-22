package org.mm3.data;

import org.mm3.model.MM3DataPacket;
import org.mm3.util.ByteRingBuffer;

/**
 * Created by CowboyJim on 7/8/15.
 */
public class MM3StreamParser implements DataParser {

    private static final byte syncByte_1 = 0x0A;
    private static final byte syncByte_2 = 0x05;
    /**
     * All EKG data packet are 39 bytes.
     */
    private static final int EKG_PACKET_SIZE = 39;
    protected static ByteRingBuffer buffer = new ByteRingBuffer(256);
    protected byte currentSyncByte = -1;

    protected MM3PacketListener listener;

    @Override
    public void setPacketListener(MM3PacketListener listener) {
        this.listener = listener;
    }

    /**
     * @param bytes
     */
    public void parseData(byte[] bytes) {

        // Write packet to buffer
        buffer.write(bytes);

        // Send packets to listener until the buffer has no more packets
        while (buffer.getUsed() > 0) {

            MM3DataPacket packet;
            if ((packet = getEKGPacket()) == null) {
                return;
            } else {

                System.out.println("Packet decoded");
                listener.packetReceived(packet);
            }
        }
    }

    /**
     * @return
     */
    protected MM3DataPacket getEKGPacket() {

        // All data packet are 39 bytes. We cannot sync until the buffer has filled with sufficient data
        if (buffer.getUsed() < EKG_PACKET_SIZE) {
            return null;
        }

        if (!isSyncedToBuffer()) {
            return null;
        }
        int packetLength = buffer.getByteAt(1);

        if (packetLength != EKG_PACKET_SIZE) {
            buffer.discard(packetLength);
            currentSyncByte = flipSyncByte(currentSyncByte);
            return null;
        }

        // Read the EKG packet data from the buffer and flip the sync byte
        byte packetByteArray[] = new byte[packetLength + 1];
        buffer.read(packetByteArray);
        currentSyncByte = flipSyncByte(currentSyncByte);

        return new MM3DataPacket(packetByteArray);
    }

    /**
     * @return
     */
    protected boolean isSyncedToBuffer() {
        if (currentSyncByte == -1) {

            // All data packet are 39 bytes. We cannot sync until the buffer has filled with sufficient data
            if (buffer.getUsed() < EKG_PACKET_SIZE) {
                return false;
            }

            int syncIndex = findInitialSyncByteIndex();
            if (syncIndex != -1) {
                if (isValidPacketFound(syncIndex)) {
                    currentSyncByte = buffer.getByteAt(syncIndex);
                    // Throw away the incomplete bytes at the head of the buffer
                    buffer.discard(syncIndex);
                    return true;
                }
            }
        }
        return true;
    }

    protected boolean isValidPacketFound(int syncByteIndex) {

        // Don't proceed unless we find a sync byte
        if (syncByteIndex == -1) return false;
        int len = buffer.getByteAt(syncByteIndex + 1);
        if (len == 0) return false;

        byte foundSyncByte = buffer.getByteAt(syncByteIndex);
        byte syncByte = buffer.getByteAt(syncByteIndex + len);
        return syncByte == flipSyncByte(foundSyncByte);
    }

    /**
     * @return
     */
    private int findInitialSyncByteIndex() {
        int index0_1;
        int index0_2;
        int index1_1;
        int index1_2;
        int byteIndex = -1;

        index0_1 = buffer.indexOf(syncByte_1);
        index0_2 = buffer.indexOf(syncByte_1, index0_1 + 1);
        index1_1 = buffer.indexOf(syncByte_2);
        index1_2 = buffer.indexOf(syncByte_2, index1_1 + 1);

        boolean id0_1_valid = validSyncByte(index0_1, syncByte_1);
        boolean id0_2_valid = validSyncByte(index0_2, syncByte_1);
        boolean id1_1_valid = validSyncByte(index1_1, syncByte_2);
        boolean id1_2_valid = validSyncByte(index1_2, syncByte_2);

        if (id0_1_valid) {
            byteIndex = index0_1;
        } else if (id0_2_valid) {
            byteIndex = index0_2;
        } else if (id1_1_valid) {
            byteIndex = index1_1;
        } else if (id1_2_valid) {
            byteIndex = index1_2;
        }
        return byteIndex;
    }

    /**
     * Ensure that the sync byte isn't just a data byte;
     * 1) find the sync byte index
     * 2) read the next byte which is the packet size
     * 3) move to the end of the packet and make sure they next sync
     * is the other sync byte
     * 4) If it is, then the sync byte index is valid. False otherwise
     *
     * @param index    index buffer index to start the validation
     * @param syncByte syncIndex index of the _gSyncBytes array used to determine the desired sync byte
     * @return
     */
    protected boolean validSyncByte(int index, byte syncByte) {

        if (index == -1) return false;

        int packetLength = buffer.getByteAt(index + 1);
        int nextSyncByte = buffer.getByteAt(index + packetLength);

        if (nextSyncByte == -1) return false;
        return nextSyncByte == flipSyncByte(syncByte);
    }

    protected byte flipSyncByte(byte syncByte) {
        return (syncByte == syncByte_1) ? syncByte_2 : syncByte_1;
    }
}
