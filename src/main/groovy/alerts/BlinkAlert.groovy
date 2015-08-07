import org.mm3.alerts.Alert
import org.mm3.model.MM3DataPacket

/**
 * The alert will trigger every 5 events
 *
 * Created by CowboyJim on 8/5/15.
 *
 */
class BlinkAlert implements Alert {

    def count = 0

    String getName() {
        "Blink5Alert"
    }

    boolean isConditionMet(MM3DataPacket packet) {
        count++

        if (count % 5 == 0) {
            return true
        }
        return false
    }
}
