package org.mm3.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.mm3.alerts.AlertManager;
import org.mm3.config.AppConfig;
import org.mm3.config.SpringConfig;
import org.mm3.data.MM3EventGenerator;
import org.mm3.model.EKGDataPacket;
import org.mm3.model.MM3DataPacket;
import org.mm3.util.CommonDialogs;
import org.mm3.util.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/13/15
 */
public class CaptureTablePanelController implements Observer, NestedController {

    public static final String[] HEADER = new String[]{"Seq", "EMG_L", "0.75", "1.5", "3", "4.5", "6", "7.5", "9", "10.5", "12.5", "15", "19", "24", "30", "38",
            "EMG_R", "0.75", "1.5", "3", "4.5", "6", "7.5", "9", "10.5", "12.5", "15", "19", "24", "30", "38"};

    private final ConversionUtils conversionUtils = new ConversionUtils();
    protected Logger LOG = LoggerFactory.getLogger(CaptureTablePanelController.class);
    protected SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss");
    @Autowired
    private CommonDialogs commonDialogs;
    @Autowired
    private MM3EventGenerator eventGenerator;
    @Autowired
    private SpringConfig springConfig;
    @Autowired
    private AlertManager alterManager;

    private MainController mainController;
    @Autowired
    private AppConfig appConfig;
    private ObservableList<EKGDataPacket> ekgData = FXCollections.observableArrayList();

    private BooleanProperty dataNotPresent = new SimpleBooleanProperty(true);

    private boolean capturing = false;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private ToggleButton startCaptureBtn;
    @FXML
    private ToggleButton stopCaptureBtn;
    @FXML
    private Button clearDataBtn;
    @FXML
    private MenuItem saveDataMenu;
    @FXML
    private MenuItem loadDataMenu;
    @FXML
    private MenuItem exportDataMenu;

    @FXML
    private TableView<EKGDataPacket> ekgDataTable;
    @FXML
    private TableColumn<EKGDataPacket, String> sequence;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh1;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh2;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh3;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh4;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh5;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh6;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh7;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh8;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh9;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh10;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh11;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh12;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh13;
    @FXML
    private TableColumn<EKGDataPacket, String> lCh14;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh1;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh2;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh3;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh4;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh5;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh6;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh7;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh8;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh9;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh10;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh11;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh12;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh13;
    @FXML
    private TableColumn<EKGDataPacket, String> rCh14;
    @FXML
    private TableColumn<EKGDataPacket, String> triggeredAlerts;


    @FXML
    private void initialize() {

        sequence.setCellValueFactory(cellData -> cellData.getValue().sequenceProperty());
        lCh1.setCellValueFactory(cellData -> cellData.getValue().lCh1Property());
        lCh2.setCellValueFactory(cellData -> cellData.getValue().lCh2Property());
        lCh3.setCellValueFactory(cellData -> cellData.getValue().lCh3Property());
        lCh4.setCellValueFactory(cellData -> cellData.getValue().lCh4Property());
        lCh5.setCellValueFactory(cellData -> cellData.getValue().lCh5Property());
        lCh6.setCellValueFactory(cellData -> cellData.getValue().lCh6Property());
        lCh7.setCellValueFactory(cellData -> cellData.getValue().lCh7Property());
        lCh8.setCellValueFactory(cellData -> cellData.getValue().lCh8Property());
        lCh9.setCellValueFactory(cellData -> cellData.getValue().lCh9Property());
        lCh10.setCellValueFactory(cellData -> cellData.getValue().lCh10Property());
        lCh11.setCellValueFactory(cellData -> cellData.getValue().lCh11Property());
        lCh12.setCellValueFactory(cellData -> cellData.getValue().lCh12Property());
        lCh13.setCellValueFactory(cellData -> cellData.getValue().lCh13Property());
        lCh14.setCellValueFactory(cellData -> cellData.getValue().lCh14Property());

        rCh1.setCellValueFactory(cellData -> cellData.getValue().rCh1Property());
        rCh2.setCellValueFactory(cellData -> cellData.getValue().rCh2Property());
        rCh3.setCellValueFactory(cellData -> cellData.getValue().rCh3Property());
        rCh4.setCellValueFactory(cellData -> cellData.getValue().rCh4Property());
        rCh5.setCellValueFactory(cellData -> cellData.getValue().rCh5Property());
        rCh6.setCellValueFactory(cellData -> cellData.getValue().rCh6Property());
        rCh7.setCellValueFactory(cellData -> cellData.getValue().rCh7Property());
        rCh8.setCellValueFactory(cellData -> cellData.getValue().rCh8Property());
        rCh9.setCellValueFactory(cellData -> cellData.getValue().rCh9Property());
        rCh10.setCellValueFactory(cellData -> cellData.getValue().rCh10Property());
        rCh11.setCellValueFactory(cellData -> cellData.getValue().rCh11Property());
        rCh12.setCellValueFactory(cellData -> cellData.getValue().rCh12Property());
        rCh13.setCellValueFactory(cellData -> cellData.getValue().rCh13Property());
        rCh14.setCellValueFactory(cellData -> cellData.getValue().rCh14Property());
        triggeredAlerts.setCellValueFactory(cellData -> cellData.getValue().alertsTriggeredProperty());


        // ekgData.add(new EKGDataPacket(HEADER));
/*       ekgData.add(new EKGDataPacket(1, new byte[]{0x05, (byte) 0x27, (byte) 0x93, (byte) 0x04, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x1C, (byte) 0x60, (byte) 0x9A,
                (byte) 0xB3, (byte) 0xCC, (byte) 0xE9, (byte) 0xff, (byte) 0xff, (byte) 0xF1, (byte) 0xD8, (byte) 0xBF, (byte) 0x9A,
                0x60, (byte) 0x19, (byte) 0x03, (byte) 0x1C, (byte) 0x60, (byte) 0x9A, (byte) 0xB3, (byte) 0xCC, (byte) 0xE9, (byte) 0xff, (byte) 0xff, (byte) 0xF1,
                (byte) 0xD8, (byte) 0xBF, (byte) 0x9A, (byte) 0x60, (byte) 0x19}));
        ekgData.add(new EKGDataPacket(2, new byte[]{0x0a, (byte) 0x27, (byte) 0x93, (byte) 0x04, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x1C, (byte) 0x60, (byte) 0x9A,
                (byte) 0xB3, (byte) 0xCC, (byte) 0xE9, (byte) 0xff, (byte) 0xff, (byte) 0xF1, (byte) 0xD8, (byte) 0xBF, (byte) 0x9A,
                0x60, (byte) 0x19, (byte) 0x03, (byte) 0x1C, (byte) 0x60, (byte) 0x9A, (byte) 0xB3, (byte) 0xCC, (byte) 0xE9, (byte) 0xff, (byte) 0xff, (byte) 0xF1,
                (byte) 0xD8, (byte) 0xBF, (byte) 0x9A, (byte) 0x60, (byte) 0x19}));*/

        ekgDataTable.setItems(ekgData);
        progressBar.setVisible(false);

        saveDataMenu.disableProperty().bindBidirectional(dataNotPresent);
        clearDataBtn.disableProperty().bindBidirectional(dataNotPresent);
        exportDataMenu.disableProperty().bindBidirectional(dataNotPresent);

/*        ekgDataTable.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter Key ");
            }
            event.consume();
        });*/

        startCaptureBtn.setOnAction(event -> {
            toggleCapture(true);
        });

        stopCaptureBtn.setOnAction(event -> {
            toggleCapture(false);
        });

        exportDataMenu.setOnAction(event -> {
            exportTableToCSV();
        });

        loadDataMenu.setOnAction(event -> {
            loadTableFromFile();
        });

        /**
         *
         */
        clearDataBtn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the table data?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                clearTableData();
            } else {
                LOG.debug("Cancel Data clear");
            }
        });

        saveDataMenu.setOnAction(event -> {
            saveTableDataToFile();
        });

        setupRowFactory();
    }

    /**
     * Change the row color whenever an alert is fired
     */
    protected void setupRowFactory() {

        PseudoClass alert = PseudoClass.getPseudoClass("alert");

        ekgDataTable.setRowFactory(tableView -> {
            TableRow<EKGDataPacket> row = new TableRow<>();

            row.itemProperty().addListener((obs, previousAlert, currentAlert) -> {

                if (currentAlert != null) {
                    row.pseudoClassStateChanged(alert, ((EKGDataPacket) currentAlert).getAlertsTriggered().length() > 0);

                } else {
                    row.pseudoClassStateChanged(alert, false);

                }
            });
            return row;
        });
    }

    @Override
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    private void clearTableData() {
        ekgData.remove(0, ekgData.size());
        dataNotPresent.set(true);
        mainController.setStatusMessage("Table cleared");
    }

    /**
     * @param state
     */
    private void toggleCapture(boolean state) {
        LOG.debug("Capture on: " + state);
        if (state) {
            eventGenerator.addObserver(this);

        } else {
            eventGenerator.deleteObserver(this);
        }

        capturing = state;
        progressBar.setVisible(capturing);
    }

    @Override
    public void update(Observable o, Object mm3Packet) {
        if (capturing) {
            if (dataNotPresent.get() == true) {
                dataNotPresent.set(false);
            }
            EKGDataPacket packet = new EKGDataPacket(MM3DataPacket.getSequenceNum(), ((MM3DataPacket) mm3Packet).getByteArray());


            // check all alerts conditions
            Map<String, Boolean> alerts = alterManager.checkAlertStatus((MM3DataPacket) mm3Packet);

            StringWriter firedAlerts = new StringWriter();
            for (String key : alerts.keySet()) {

                boolean triggered = alerts.get(key);
                if (triggered) {
                    firedAlerts.append(key);
                    firedAlerts.append(" ");
                }
            }

            // Add fired alerts if any
            packet.setAlertTriggered(firedAlerts.toString());

            ekgData.add(packet);
        }
    }

    protected void saveTableDataToFile() {

        File outputFile = getOutputFile("eeg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outputFile);
            for (int x = 0; x < ekgData.size(); x++) {
                fos.write(ConversionUtils.leIntToByteArray(ekgData.get(x).getPacketNum()));
                fos.write(ekgData.get(x).getPacket());
            }
            mainController.setStatusMessage("File successfully saved");

        } catch (Exception e) {
            LOG.error("Exception while saving file to disk", e);
            mainController.setStatusMessage("An exception occurred! The file was not saved");
            commonDialogs.showExceptionDialog(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LOG.error("Exception while closing output stream", e);
                    commonDialogs.showExceptionDialog(e);
                }
            }
        }
    }

    private File getOutputFile(String fileExtension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File to Disk");
        File directory = new File(appConfig.getDefaultDirectory());
        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdir();
        }
        fileChooser.setInitialDirectory(directory);
        fileChooser.setInitialFileName(dataFormatter.format(new Date()) + "." + fileExtension);
        return fileChooser.showSaveDialog(springConfig.getPrimaryStage());
    }

    protected void exportTableToCSV() {
        int i;
        PrintStream ps = null;
        try {
            File outputFile = getOutputFile("csv");
            ps = new PrintStream(outputFile);

            for (i = 0; i < HEADER.length; i++) {
                ps.print(HEADER[i]);
                if (i < HEADER.length - 1) {
                    ps.print(",");
                }
            }
            ps.println();

            for (i = 0; i < ekgData.size(); i++) {
                ps.print(ekgData.get(i).getPacketNum() + ",");
                byte[] data = ekgData.get(i).getPacket();
                for (int x = 0; x < data.length; x++) {
                    ps.print(String.valueOf(data[x] & 0xFF));
                    if (x < data.length - 1) {
                        ps.print(",");
                    }
                }
                ps.println();
            }
            ps.flush();

            mainController.setStatusMessage("File successfully saved");

        } catch (Exception e) {
            LOG.error("Exception while exporting file", e);
            mainController.setStatusMessage("An exception occurred! The data was not exported");
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    protected void loadTableFromFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load data from disk");
        File directory = new File(appConfig.getDefaultDirectory());
        fileChooser.setInitialDirectory(directory);
        fileChooser.setInitialFileName(dataFormatter.format(new Date()) + ".eeg");
        File file = fileChooser.showOpenDialog(springConfig.getPrimaryStage());

        if (file != null) {
            clearTableData();
            try {
                FileInputStream fis = new FileInputStream(file);
                byte sequence[] = new byte[4];
                byte data[] = new byte[39];

                while (fis.read(sequence) != -1) {
                    fis.read(data);
                    ekgData.add(new EKGDataPacket(ConversionUtils.byteArrayToLeInt(sequence), data));
                }

                dataNotPresent.set(false);
                mainController.setStatusMessage("Data successfully imported");

            } catch (Exception e) {
                String msg = "An exception occurred! The data was not loaded";
                LOG.error(msg, e);
                mainController.setStatusMessage(msg);
            }
        }
    }
}
