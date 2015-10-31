package org.mm3.util;

/**
 * A ring buffer (circular buffer, FIFO) for bytes.
 * <p/>
 * <p>All methods of this class are non-blocking and not synchronized (not thread-safe).
 */
public class ByteRingBuffer {

    private byte[] rBuf;                         // ring buffer data
    private int rBufSize;                     // ring buffer size
    private int rBufPos;                      // position of first (oldest) data byte within the ring buffer
    private int rBufUsed;                     // number of used data bytes within the ring buffer

    /**
     * Creates a ring buffer.
     */
    public ByteRingBuffer(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        rBufSize = size;
        rBuf = new byte[rBufSize];
    }

    /**
     * Resizes the ring buffer by preserving it's data.
     * <p/>
     * <p>If the new size is not enough to keep all used data in the ring buffer,
     * the excess old data is discarded.
     */
    public void resize(int newSize) {
        if (newSize <= 0) {
            throw new IllegalArgumentException();
        }
        if (newSize < rBufUsed) {                               // if new buffer is too small to contain all data
            discard(rBufUsed - newSize);
        }                       // discard oldest data
        byte[] newBuf = new byte[newSize];
        int newBufUsed = read(newBuf, 0, newSize);              // transfer data to new buffer
        rBuf = newBuf;
        rBufSize = newSize;
        rBufPos = 0;
        rBufUsed = newBufUsed;
    }

    /**
     * Returns the size of the ring buffer.
     */
    public int getSize() {
        return rBufSize;
    }

    /**
     * Returns the number of free bytes within the ring buffer.
     */
    public int getFree() {
        return rBufSize - rBufUsed;
    }

    /**
     * Returns the number of used bytes within the ring buffer.
     */
    public int getUsed() {
        return rBufUsed;
    }

    /**
     * Clears the ring buffer.
     */
    public void clear() {
        rBufPos = 0;
        rBufUsed = 0;
    }

    /**
     * Discards the oldest <code>len</code> bytes within the ring buffer.
     * This has the same effect as calling <code>read(new byte[len], 0, len)</code>.
     *
     * @param len The number of bytes to be discarded.
     */
    public void discard(int len) {
        if (len < 0) {
            return;
            //throw new IllegalArgumentException();
        }
        int trLen = Math.min(len, rBufUsed);
        rBufPos = clip(rBufPos + trLen);
        rBufUsed -= trLen;
    }

    /**
     * Writes data to the ring buffer.
     *
     * @return The number of bytes written.
     *         This is guaranteed to be <code>min(len, getFree())</code>.
     */
    public int write(byte[] buf, int pos, int len) {
        if (len < 0) {
            throw new IllegalArgumentException();
        }
        if (rBufUsed == 0) {
            rBufPos = 0;
        }                                       // (speed optimization)
        int p1 = rBufPos + rBufUsed;
        if (p1 < rBufSize) {                                    // free space in two pieces
            int trLen1 = Math.min(len, rBufSize - p1);
            append(buf, pos, trLen1);
            int trLen2 = Math.min(len - trLen1, rBufPos);
            append(buf, pos + trLen1, trLen2);
            return trLen1 + trLen2;
        } else {                                                 // free space in one piece
            int trLen = Math.min(len, rBufSize - rBufUsed);
            append(buf, pos, trLen);
            return trLen;
        }
    }

    /**
     * Writes data to the ring buffer.
     * <p/>
     * <p>Convenience method for: <code>write(buf, 0, buf.length)</code>
     */
    public int write(byte[] buf) {
        return write(buf, 0, buf.length);
    }

    private void append(byte[] buf, int pos, int len) {
        if (len == 0) {
            return;
        }
        if (len < 0) {
            throw new AssertionError();
        }
        int p = clip(rBufPos + rBufUsed);
        System.arraycopy(buf, pos, rBuf, p, len);
        rBufUsed += len;
    }

    /**
     * Reads data from the ring buffer.
     *
     * @return The number of bytes read.
     *         This is guaranteed to be <code>min(len, getUsed())</code>.
     */
    public int read(byte[] buf, int pos, int len) {
        if (len < 0) {
            throw new IllegalArgumentException();
        }
        int trLen1 = Math.min(len, Math.min(rBufUsed, rBufSize - rBufPos));
        remove(buf, pos, trLen1);
        int trLen2 = Math.min(len - trLen1, rBufUsed);
        remove(buf, pos + trLen1, trLen2);
        return trLen1 + trLen2;
    }

    /**
     * @param searchByte
     * @return
     */
    public int indexOf(byte searchByte) {
        return indexOf(searchByte, 0);
    }

    /**
     * @param searchByte
     * @param offset
     * @return
     */
    public int indexOf(byte searchByte, int offset) {
        int index = offset;
        //traverse ring until match is found
        for (int x = (rBufPos + offset) % rBufSize; index < getUsed(); x = (x + 1) % rBufSize) {
            if (searchByte == rBuf[x]) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * @param index
     * @return
     */
    public byte getByteAt(int index) {
        if (getUsed() <= index) {
            return -1;
        }
        return rBuf[(rBufPos + index) % rBufSize];
    }

    /**
     * Reads data from the ring buffer.
     * <p/>
     * <p>Convenience method for: <code>read(buf, 0, buf.length)</code>
     */
    public int read(byte[] buf) {
        return read(buf, 0, buf.length);
    }

    private void remove(byte[] buf, int pos, int len) {
        if (len == 0) {
            return;
        }
        if (len < 0) {
            throw new AssertionError();
        }
        System.arraycopy(rBuf, rBufPos, buf, pos, len);
        rBufPos = clip(rBufPos + len);
        rBufUsed -= len;
    }

    private int clip(int p) {
        return p % rBufSize;
    }
}
