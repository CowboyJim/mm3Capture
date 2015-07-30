package org.mm3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import jssc.SerialPortList;
import org.mm3.config.AppConfig;
import org.mm3.config.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/27/15
 */
public class ConfigurationDialog extends Stage implements Initializable {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private SpringConfig springConfig;

    @FXML
    private ComboBox<String> comPortChoice;

    @FXML
    private Label alertDirectoryLbl;

    @FXML
    private Label captureDirectoryLbl;

    @FXML
    private Button selectDirBtn;

    @FXML
    private Button selectCaptureDirBtn;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    public ConfigurationDialog() {
        setTitle("Set Configuration Data");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("ConfigurationDialog.fxml"));
        fxmlLoader.setController(this);

        try {
            setScene(new Scene((Parent) fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectDirBtn.setOnAction(event -> {
            File selectedDirectory = getSelectedDirectory("Select Directory For Alert Files");
            if (selectedDirectory != null) {
                alertDirectoryLbl.setText(selectedDirectory.getAbsolutePath());
            }
        });

        selectCaptureDirBtn.setOnAction(event -> {
            File selectedDirectory = getSelectedDirectory("Select Directory For Capture Files");
            if (selectedDirectory != null) {
                captureDirectoryLbl.setText(selectedDirectory.getAbsolutePath());
            }
        });

    }

    private File getSelectedDirectory(String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(springConfig.getPrimaryStage());
    }

    @PostConstruct
    public void init() {
        String portID;
        String[] portNames = SerialPortList.getPortNames();

        ObservableList<String> options = FXCollections.observableArrayList(portNames);
        comPortChoice.getItems().addAll(options);
        if (portNames.length > 0) {
            portID = appConfig.getPortID();

        } else {
            portID = "No COM Ports Found";
            appConfig.setPortID(portID);
        }

        // Set current values
        comPortChoice.setPromptText(portID);
        alertDirectoryLbl.setText(appConfig.getAlertDirectory());
        captureDirectoryLbl.setText(appConfig.getDefaultDirectory());
    }

    @FXML
    void onCancelButtonAction(ActionEvent event) {
        close();
    }

    @FXML
    void onOKButtonAction(ActionEvent event) {

        appConfig.setAlertDirectory(alertDirectoryLbl.getText());
        appConfig.setDefaultDirectory(captureDirectoryLbl.getText());

        if (comPortChoice.getValue() == null) {
            appConfig.setPortID("unknown");
        } else {
            appConfig.setPortID(comPortChoice.getValue());
        }
        appConfig.saveProperties();
        close();
    }
}
