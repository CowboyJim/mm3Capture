package org.mm3.data;

import org.mm3.util.ByteArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by CowboyJim on 7/8/15.
 */
public class ByteStreamParser {

    protected static ByteArrayQueue buffer = new ByteArrayQueue(256);

    private static byte syncByte_1 = 0x0A;
    private static byte syncByte_2 = 0x05;
    //private static byte synchByteArray[] = new byte[]{syncByte_1, syncByte_2};

    protected byte currentSyncByte = -1;


    @Autowired
    protected PacketReceivedCallback callback;

    public void setCallback(PacketReceivedCallback callback) {
        this.callback = callback;
    }

    /**
     * @param bytes
     */
    public void bytesReceived(byte[] bytes) {

        MM3DataPacket packet;

        buffer.add(bytes);

        while (buffer.length() > 0) {

            if ((packet = findPacket()) == null) {
                return;
            } else {
                callback.packetReceived(packet);
            }
        }
    }

    protected MM3DataPacket findPacket() {
        SyncByteData xxx = findNextScanByteIndex();

        return null;
    }

    protected SyncByteData findNextScanByteIndex() {
        int index0_1 = -1, index1_1 = -1, index0_2 = -1, index1_2 = -1;
        SyncByteData syncByteData = null;

        if (currentSyncByte == -1) {
            syncByteData = this.findInitialSyncByte();
            currentSyncByte = syncByteData.syncByte;

        } else {

            int index = indexOf(0, currentSyncByte);
            syncByteData = new SyncByteData(index, getNextSyncByte(currentSyncByte));
        }


        return syncByteData;
    }

    private SyncByteData findInitialSyncByte() {
        int index0_1;
        int index0_2;
        int index1_1;
        int index1_2;
        byte syncByte = 0;
        int byteIndex = 0;

        index0_1 = indexOf(0, syncByte_1);
        index0_2 = indexOf(index0_1 + 1, syncByte_1);
        index1_1 = indexOf(0, syncByte_1);
        index1_2 = indexOf(index1_1 + 1, syncByte_1);

        boolean id0_1_valid = validSyncByte(index0_1, syncByte_1);
        boolean id0_2_valid = validSyncByte(index0_2, syncByte_1);
        boolean id1_1_valid = validSyncByte(index1_1, syncByte_2);
        boolean id1_2_valid = validSyncByte(index1_2, syncByte_2);

        if (id0_1_valid) {
            byteIndex = index0_1;
            syncByte = 0x0A;
        } else if (id0_2_valid) {
            byteIndex = index0_2;
            syncByte = 0x0A;
        } else if (id1_1_valid) {
            byteIndex = index1_1;
            syncByte = 0x05;
        } else if (id1_2_valid) {
            byteIndex = index1_2;
            syncByte = 0x05;
        }

        return new SyncByteData(byteIndex, syncByte);
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

        if (index == -1) {
            return false;
        }
        int packetLength = buffer.array()[index + 1];
        int nextSyncByte = buffer.array()[index + packetLength];

        return nextSyncByte == getNextSyncByte(syncByte);
    }

    protected byte getNextSyncByte(byte syncByte) {
        if (syncByte == syncByte_1) {
            return syncByte_2;
        }
        return syncByte_1;
    }

    protected int indexOf(int offset, byte syncByte) {
        int index = -1;
        byte bufferArray[] = buffer.array();

        for (int i = buffer.offset(); i < buffer.length(); i++) {
            if (bufferArray[i] == syncByte) {
                return i;
            }
        }
        return index;
    }

    class SyncByteData {
        public int index;
        public byte syncByte;

        public SyncByteData(int index, byte syncByte) {
            this.index = index;
            this.syncByte = syncByte;
        }
    }


}
