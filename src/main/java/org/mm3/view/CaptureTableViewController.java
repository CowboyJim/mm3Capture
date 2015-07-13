package org.mm3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.mm3.model.EKGDataPacket;

/**
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/13/15
 */
public class CaptureTableViewController {

    private ObservableList<EKGDataPacket> ekgData = FXCollections.observableArrayList();

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
    private void initialize() {

        sequence.setCellValueFactory(cellData -> cellData.getValue().sequenceProperty());
        lEmg.setCellValueFactory(cellData -> cellData.getValue().lEmgProperty());
        lCh1.setCellValueFactory(cellData -> cellData.getValue().lCh1Property());
        lCh2.setCellValueFactory(cellData -> cellData.getValue().lCh2Property());

        ekgData.add(new EKGDataPacket(new byte[]{(byte) 0x01, (byte) 0x05, (byte) 0x27, (byte) 0x93}));
        ekgData.add(new EKGDataPacket(new byte[]{(byte) 0x02, (byte) 0xCC, (byte) 0xE9, (byte) 0xff}));

        ekgDataTable.setItems(ekgData);
    }
}
