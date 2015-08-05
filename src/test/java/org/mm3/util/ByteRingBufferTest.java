package org.mm3.util;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/10/15
 * Time: 11:17 AM
 */
public class ByteRingBufferTest {

    private ByteRingBuffer buf;

    @BeforeMethod
    public void setUp() throws Exception {
        buf = new ByteRingBuffer(16);
    }

    @Test
    public void testInsert() throws Exception {

        buf.write(new byte[]{0x01, 0x02});
        System.out.println(buf.getUsed());
        assertEquals(buf.getUsed(), 2);
        buf.write(new byte[]{0x03, 0x04, 0x05, 0x06});
        assertEquals(buf.getUsed(), 6);
        buf.write(new byte[]{0x07, 0x08});
        assertEquals(buf.getUsed(), 8);
    }

    @Test
    public void testRead() throws Exception {
        buf = new ByteRingBuffer(8);
        buf.write(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        byte out[] = new byte[4];
        buf.read(out);
        buf.write(new byte[]{0x09, 0x0a, 0x0b, 0x0c});
        assertEquals(buf.getUsed(), 8);
    }

    @Test
    public void testIndexOf() throws Exception {
        buf = new ByteRingBuffer(8);
        buf.write(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});

        byte searchFor = 0x02;
        int index = buf.indexOf(searchFor);
        assertEquals(index, 1);

        index = buf.indexOf((byte) 0x00);
        assertEquals(index, -1);

        byte out[] = new byte[4];
        buf.read(out);
        buf.write(new byte[]{0x09, 0x0a, 0x0b, 0x0c});
        // New byte array
        //0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c
        index = buf.indexOf((byte) 0x08);
        assertEquals(index, 3);

        index = buf.indexOf((byte) 0x08, 2);
        assertEquals(index, 3);

        index = buf.indexOf((byte) 0x08, 3);
        assertEquals(index, 3);
        index = buf.indexOf((byte) 0x08, 4);
        assertEquals(index, -1);
    }

    @Test
    public void testGetAt() throws Exception {
        buf = new ByteRingBuffer(8);
        buf.write(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        byte out[] = new byte[4];
        buf.read(out);
        buf.write(new byte[]{0x09, 0x0a, 0x0b, 0x0c});
        // New byte array
        //0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c
        byte b = buf.getByteAt(2);
        assertEquals(b, 0x07);

        buf.read(out, 0, 2);

        System.out.println(buf.getUsed());
        try {
            buf.getByteAt(7);
            fail("should throw an exception ");
        } catch (AssertionError e) {
        }
    }
}
