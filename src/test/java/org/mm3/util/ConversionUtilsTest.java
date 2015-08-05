package org.mm3.util;

import org.testng.annotations.Test;

/**
 * Created by jim on 8/4/15.
 */
public class ConversionUtilsTest {

    private byte[] testArray =
            new byte[]{0x05, (byte) 0x27, (byte) 0x93, (byte) 0x04, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x1C, (byte) 0x60, (byte) 0x9A,
                    (byte) 0xB3, (byte) 0xCC, (byte) 0xE9, (byte) 0xff, (byte) 0xff, (byte) 0xF1, (byte) 0xD8, (byte) 0xBF, (byte) 0x9A,
                    0x60, (byte) 0x19, (byte) 0x03, (byte) 0x1C, (byte) 0x60, (byte) 0x9A, (byte) 0xB3, (byte) 0xCC, (byte) 0xE9, (byte) 0xff, (byte) 0xff, (byte) 0xF1,
                    (byte) 0xD8, (byte) 0xBF, (byte) 0x9A, (byte) 0x60, (byte) 0x19};

    @Test
    public void testByteToInt() throws Exception {

        for (int i = 0; i < testArray.length; i++) {
            int result = ConversionUtils.byteToInt(testArray[i]);
            System.out.println(result);

        }


    }
}