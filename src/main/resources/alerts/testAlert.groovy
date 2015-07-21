import org.mm3.alerts.Alert
import org.mm3.data.MM3DataPacket


class TestAlert implements Alert{

    String name = "TestAlert"

    boolean isConditionMet(MM3DataPacket packet) {
        bytes[] packet = packet.getByteArray()


        packet.leftChannelValues();
        println "In TestAlert..."
        return false
    }
}