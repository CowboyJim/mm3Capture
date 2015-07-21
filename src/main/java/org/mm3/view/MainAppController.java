package org.mm3.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.mm3.alerts.AlertManager;
import org.mm3.config.AppConfig;
import org.mm3.config.SpringConfig;
import org.mm3.data.MM3EventGenerator;
import org.mm3.util.CommonDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/13/15
 */
public class MainAppController implements MainController {

    protected Logger LOG = LoggerFactory.getLogger(MainAppController.class);

    @Autowired
    private MM3EventGenerator comGenerator;

    @Autowired
    private CommonDialogs commonDialogs;

    @Autowired
    private SpringConfig springConfig;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AlertManager alterManager;

    private boolean comConnected = false;

    @FXML
    private ToggleButton comConnectBtn;
    @FXML
    private ToggleButton comDisconnectBtn;
    @FXML
    private MenuItem settingsDialog;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem exportMenuItem;
    @FXML
    private Label currentPortID;
    @FXML
    private Label statusOutputLbl;

    @FXML
    private CaptureTablePanelController captureTablePanelController;
    @FXML
    private VisualPanelController visualPanelController;

    @FXML
    private void initialize() {
        comConnectBtn.setOnAction(event -> {
            toggleComPort(true);
        });

        comDisconnectBtn.setOnAction(event -> {
            toggleComPort(false);
        });

        settingsDialog.setOnAction(event -> {
            showConfigurationDialog();
        });

        closeMenuItem.setOnAction(event -> {
            Platform.exit();
        });

    }


    @PostConstruct
    public void init() {
        // Bind the port property to the UI label
        currentPortID.textProperty().bindBidirectional(appConfig.comPortProperty());
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
        for (int i = 0; i < portNames.length; i++) {
            choices.add(portNames[i]);
        }
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
}
