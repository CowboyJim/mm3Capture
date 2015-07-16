package org.mm3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.mm3.data.MM3DataPacket;
import org.mm3.data.MM3EventGenerator;
import org.mm3.model.EKGDataPacket;
import org.mm3.util.CommonDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/13/15
 */
public class CaptureTablePanelController implements Observer {

    public static final String[] HEADER = new String[]{"Seq", "EMG_L", "0.75", "1.5", "3", "4.5", "6", "7.5", "9", "10.5", "12.5", "15", "19", "24", "30", "38",
            "EMG_R", "0.75", "1.5", "3", "4.5", "6", "7.5", "9", "10.5", "12.5", "15", "19", "24", "30", "38"};
    protected Logger LOG = LoggerFactory.getLogger(CaptureTablePanelController.class);
    @Autowired
    private CommonDialogs commonDialogs;
    @Autowired
    private MM3EventGenerator eventGenerator;
    private ObservableList<EKGDataPacket> ekgData = FXCollections.observableArrayList();

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
    private Button saveDataBtn;
    @FXML
    private TableView<EKGDataPacket> ekgDataTable;
    @FXML
    private TableColumn<EKGDataPacket, String> sequence;
    @FXML
    private TableColumn<EKGDataPacket, String> lEmg;
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
    private TableColumn<EKGDataPacket, String> rEmg;
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
    private void initialize() {

        sequence.setCellValueFactory(cellData -> cellData.getValue().sequenceProperty());
        lEmg.setCellValueFactory(cellData -> cellData.getValue().lEmgProperty());
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

        rEmg.setCellValueFactory(cellData -> cellData.getValue().rEmgProperty());
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

        ekgData.add(new EKGDataPacket(HEADER));
        ekgDataTable.setItems(ekgData);
        progressBar.setVisible(false);

        ekgDataTable.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            if(event.getCode() == KeyCode.ENTER)    {
                System.out.println("Enter Key ");

            }
            System.out.println(event.getCode());
            System.out.println(event.getEventType());
            event.consume();

        });

        startCaptureBtn.setOnAction(event -> {
            toggleCapture(true);
        });

        stopCaptureBtn.setOnAction(event -> {
            toggleCapture(false);
        });


        /**
         *
         */
        clearDataBtn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            //alert.setHeaderText("Delete Captured Data");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the table data?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                LOG.debug("OK to clear data");
                ekgData.remove(1, ekgData.size());
            } else {
                LOG.debug("Cancel Data clear");
            }

        });

        saveDataBtn.setOnAction(event -> {
            if (ekgData.size() <= 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Table is Already Clear");
                alert.setHeaderText(null);
                alert.setContentText("There is no data to clear");

                alert.showAndWait();
            }
        });

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
            ekgData.add(new EKGDataPacket(MM3DataPacket.getSequenceNum(), ((MM3DataPacket) mm3Packet).getByteArray()));
        }
    }
}
