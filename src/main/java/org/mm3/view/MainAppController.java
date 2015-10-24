
package org.mm3.view;

import groovy.lang.GroovyObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.FileChooser;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.mm3.alerts.AlertManager;
import org.mm3.config.AppConfig;
import org.mm3.config.SpringConfig;
import org.mm3.data.FileBasedEventGenerator;
import org.mm3.data.MM3EventGenerator;
import org.mm3.model.MM3DataPacket;
import org.mm3.util.CommonDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URI;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/13/15
 */
public class MainAppController implements MainController, Observer {

    protected Logger LOG = LoggerFactory.getLogger(MainAppController.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MM3EventGenerator comGenerator;

    @Autowired
    private CommonDialogs commonDialogs;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AlertManager alterManager;

    @Autowired
    private SpringConfig springConfig;

    @Autowired
    private FileBasedEventGenerator fileBasedEventGenerator;

    private boolean comConnected = false;
    private Map<String, Circle> alertUiMap = new LinkedHashMap<>();

    @FXML
    private ToggleButton comConnectBtn;
    @FXML
    private ToggleButton comDisconnectBtn;
    @FXML
    private MenuItem settingsDialog;
    @FXML
    private MenuItem playbackDialog;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem exportMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private Label currentPortID;
    @FXML
    private Label statusOutputLbl;
    @FXML
    private TabPane tabPane;
    @FXML
    private HBox alertVbox;
    @FXML
    private Hyperlink sourceURLLink;
    @FXML
    private CaptureTablePanelController captureTablePanelController;
    @FXML
    private VisualPanelController visualPanelController;

    public TabPane getTabPane() {
        return tabPane;
    }

    @FXML
    private void initialize() {
        comConnectBtn.setOnAction(event -> {
            toggleComPort(true);
        });

        comDisconnectBtn.setOnAction(event -> {
            toggleComPort(false);
        });

        settingsDialog.setOnAction(event -> {
            ConfigurationDialog dialog = context.getBean(ConfigurationDialog.class, null);
            dialog.showAndWait();
        });

        playbackDialog.setOnAction(event -> {
            loadPlaybackFile();
        });

        closeMenuItem.setOnAction(event -> {
            Platform.exit();
        });
        exportMenuItem.setOnAction(event -> {
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Unimplemented operation");
            alert.setHeaderText(null);
            alert.setContentText("This operation has not been implemented yet ");
            alert.showAndWait();
        });
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("About Application");
            alert.setHeaderText("Mind Mirror 3 Capture Application");
            alert.setContentText("This application allows the user to connect a Mind Mirror 3 device directly to a COM port via " +
                    "the fiber optic isolation cable and capture real-time EEG data from the device");
            alert.showAndWait();
        });
        sourceURLLink.setOnAction(event -> {
            try {
                java.awt.Desktop.getDesktop().browse(new URI("https://github.com/CowboyJim/mm3Capture"));
            } catch (Exception e) {
                LOG.error("Error opening source git hub site", e);
            }
        });
    }


    @PostConstruct
    public void init() {
        // Bind the port property to the UI label
        currentPortID.textProperty().bindBidirectional(appConfig.comPortProperty());

        // Dynamically create all alerts
        Map<String, GroovyObject> alerts = alterManager.getAlertClasses();
        for (String key : alerts.keySet()) {
            Circle alarm = new Circle(11.0, Color.web("green"));
            alarm.setRadius(11.0);
            alarm.setStrokeType(StrokeType.INSIDE);
            alarm.setStroke(Color.web("black"));
            Label label = new Label(key);
            alertVbox.getChildren().addAll(alarm, label);
            alertUiMap.put(key, alarm);
        }

        comGenerator.addObserver(this);
    }

    /**
     * @param connect
     */
    private void toggleComPort(boolean connect) {
        LOG.debug("Com port connect: " + connect);

        if (connect) {
            try {
                comGenerator.connectToSerialPort();
                comConnected = true;
            } catch (SerialPortException ex) {
                commonDialogs.showExceptionDialog(ex);
                comConnected = false;
                LOG.error(ex.getMessage(), ex);
            }
        } else {
            try {
                comGenerator.disconnectFromSerialPort();
                comConnected = false;
            } catch (SerialPortException ex) {
                commonDialogs.showExceptionDialog(ex);
                comConnected = true;
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    private void showConfigurationDialog() {

        String portID = null;
        List<String> choices = new ArrayList<>();

        String[] portNames = SerialPortList.getPortNames();
        Collections.addAll(choices, portNames);

        if (choices.size() > 0) {
            portID = choices.get(0);
        } else {
            portID = "No COM Ports Found";
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(portID, choices);
        dialog.setTitle("COM Port Dialog");
        dialog.setHeaderText("Serial Port Selection Dialog");
        dialog.setContentText("Choose the serial port that the Mind Mirror 3 is connected to:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(port -> {
            appConfig.setPortID(port);
            appConfig.saveProperties();

        });
    }

    @Override
    public void setStatusMessage(String message) {
        statusOutputLbl.setText(message);
    }

    @Override
    public void update(Observable o, Object mm3Packet) {

        // check all alerts conditions
        Map<String, Boolean> alerts = alterManager.checkAlertStatus((MM3DataPacket) mm3Packet);

        for (String key : alerts.keySet()) {

            Circle alertImage = alertUiMap.get(key);
            boolean triggered = alerts.get(key);
            if (triggered) {
                alertImage.setFill(Color.web("red"));
            } else {
                alertImage.setFill(Color.web("green"));
            }
        }
    }

    protected void loadPlaybackFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File from Disk");
        File directory = new File(appConfig.getDefaultDirectory());
        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdir();
        }
        fileChooser.setInitialDirectory(directory);
        File file = fileChooser.showOpenDialog(springConfig.getPrimaryStage());
        if (file != null) {
            fileBasedEventGenerator.setFile(file);
        }
    }

}
