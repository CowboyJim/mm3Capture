package org.mm3.data;

import org.mm3.model.MM3DataPacket;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 */
public class FileBasedEventGeneratorTest implements MM3PacketListener {

    public static final String TEST_FILE_1 = "src/test/resources/mm3_capture_file1";
    private DataParser parser;
    private FileBasedEventGenerator generator;

    @BeforeMethod
    public void setUp() throws Exception {
        parser = new MM3StreamParser();
        parser.setPacketListener(this);
        generator = new FileBasedEventGenerator();
        generator.setParser(parser);
    }

    @Test
    public void testPlay() throws Exception {

        generator.setFileName(TEST_FILE_1);

        generator.play();

        Thread.sleep(3000);


    }

    @Override
    public void packetReceived(MM3DataPacket packet) {
        System.out.println("Packet received");
    }
}
