package org.mm3.data;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/13/15
 */
public interface DataParser {

    void setPacketListener(MM3PacketListener listener);
    void parseData(byte[] bytes);

    void reset();
}
