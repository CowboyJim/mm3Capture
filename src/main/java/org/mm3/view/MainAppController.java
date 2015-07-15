package org.mm3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.mm3.data.MM3DataPacket;
import org.mm3.data.MM3EventGenerator;
import org.mm3.model.EKGDataPacket;
import org.mm3.util.CommonDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/13/15
 */
public class MainAppController {

    protected Logger LOG = LoggerFactory.getLogger(MainAppController.class);

    @Autowired
    private MM3EventGenerator comGenerator;

    @Autowired
    private CommonDialogs commonDialogs;

    private boolean comConnected = false;

    @FXML
    private ToggleButton comConnectBtn;
    @FXML
    private ToggleButton comDisconnectBtn;
    @FXML
    private MenuItem settingsDialog;

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

    private String showConfigurationDialog() {

        String portID = null;
        List<String> choices = new ArrayList<>();

        String[] portNames = SerialPortList.getPortNames();
        for (int i = 0; i < portNames.length; i++) {
            choices.add(portNames[i]);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("b", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Look, a Choice Dialog");
        dialog.setContentText("Choose your letter:");

        Optional<String> result = dialog.showAndWait();


// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(port -> {

            System.out.println("Port ID: " + port);
        });


        return portID;
    }

}
