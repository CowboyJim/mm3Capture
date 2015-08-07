# Mind Mirror 3 Realtime Monitoring an Playback Application

The [Mind Mirror 3](http://www.mindmirroreeg.com/w/equipment/mm3/mm3.htm) (MM3) device is a real-time brain wave spectrum 
analyzer. It is a standalone EEG that measures and displays the electrical activity in the brain. The device has an LCD
 display the shows the brain wave data and allows you to save and export data. There is also a fiber optic output
 that allows you to capture the signals every 500ms via a COM port.

![Mind Mirror 3](https://github.com/CowboyJim/mm3Capture/blob/master/images/Mm3-small.jpg)

This application is used to connect to the COM port and read the data packets that are emitted from the device.  The data
can be displayed on two panels: a display panel which shows the data graphically similar to the LCD display on the device,
and a capture panel that displays the data in real-time as it is received. 

## Capture Panel
![Capture Panel](https://github.com/CowboyJim/mm3Capture/blob/master/images/capture_panel.jpg)

The capture panel will display data from the MM3 device as it is received from the COM port. Once the software
is connected to the COM port and the MM3 is emitting data, the used can start and stop capturing of the data. This
function allows the user to investigate the response of brain waves to various stimuli and isolate the data to just
those samplings that he is interested in. The table can be cleared to repeat the experiment, or the data can be saved
to a file to view later.  It can also be exported in a CSV format so it can be imported to a spreadsheet. 

## Display Panel
![Display Panel](https://github.com/CowboyJim/mm3Capture/blob/master/images/display_panel.jpg)

The display panel allows the user to view the EEG data in real-time in a stacked bar chart similar to the LCD
 display that is on the front of the device.  Data is only viewed on this panel and cannot be saved. Switch to the 
  capture panel to save data.

### Running the Application

You can download a distribution from the *dist* directory. Unzip (or unTar) the file into a directory. There is a simple
batch script (mm3Capture.bat) that can be used for Windows and a shell script (mm3Capture.sh) to run on a Unix-based
 system. You should change the log directory to a folder on your computer. The scripts expect Java 8 executable 
 on your system path. 
   
```
\#/bin/sh
export LOG_DIR=/tmp
export LOG_LEVEL=DEBUG

java -jar Mm3Capture.jar -DLOG_DIR=$LOG_DIR -DLOG_LEVEL=$DEBUG > console.log &   
```
 
## Setup and Configuration 

![Configuration Dialog](https://github.com/CowboyJim/mm3Capture/blob/master/images/configuration.jpg)

You must configure the application prior to connecting to the MM3 Device. 

* COM Port - all COM ports detected will be listed. Choose the one that the MM3 is connected to
* Alert Directory - directory where alert class files should be placed. The app will scan this directory for \*.groovy files
at startup
* Capture Directory - directory where all files saved while in the capture panel will be saved. Capture files are stored
as raw MM3 packets. You may load them into the application, or export them as CSV files

## Alerts

A researcher might want to look for correlations in the data as he is conducting experiments. The built-in alert framework
 provides a way for him do to this and alert him visually when the condition exists. Alert code must be written by the user
  and it requires writing Groovy code.  The alert is display in the alert panel and will show **green** when the alert condition
  is not true, and **red** when it is true. The following are the steps for creating alerts:
   
1. Define the location of an alert directory in the configuration settings
2. Write an alert Groovy class file the implements `org.mm3.alerts.Alert` interface. There should be one class per file
3. Drop the alert class file into the alert directory

When the application starts up, it will read all of the alert Groovy class files in the alert directory and add a visual
representation of the alert to the alerts panel.  If there is an error in the alert Groovy class, then the application
will throw and exception as soon as the first COM packet is sent to the alert for processing.


### Writing an alert file

All alerts must implement the `org.mm3.alerts.Alert` Java interface shown below. The files must be written in Groovy---one
class per file. 

```java
public interface Alert {

    String getName();
    boolean isConditionMet(MM3DataPacket packet);

}
```

The alert will be passed `org.mm3.model.MM3DataPacket` packet each time an event is received. It can examine the packet
and decide whether or not the packet meets to condition that satisfies the implemented logic. The `org.mm3.model.MM3DataPacket`
class has several methods that can be used to grab all of the data in the packet payload

* `public int[] getRightChannelValues()`
* `public int[] getLeftChannelValues()`
* `public byte[] getByteArray()`
* `public int[] getIntArray()`

```groovy
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
```

## Building the Application

The application can be built using Maven. The POM file includes the profile *deploy_build* which should be used
to create the deployment bundle.

## Known Issues

1. The application must synchronize with the MM3 device to find the beginning of valid packets. Logic exists in
the code to find packet boundaries, but there are times when the sync does not work properly. If the app does not
recognize the packets even thought the MM3 device is on, follow these steps for now
* Exit the application
* Turn the MM3 off
* Start the application
* Connect to the COM port
* Turn the MM3 on

