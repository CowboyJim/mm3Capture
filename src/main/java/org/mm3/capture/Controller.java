package org.mm3.capture;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Controller implements Initializable {


    public class Item {
        public SimpleLongProperty id = new SimpleLongProperty();
        public SimpleStringProperty name = new SimpleStringProperty();
        public SimpleIntegerProperty qty = new SimpleIntegerProperty();
        public SimpleStringProperty price = new SimpleStringProperty();

        public Long getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getPrice() {
            return price.get();
        }

        public Integer getQty() {
            return qty.get();
        }
    }

    // The table and columns
    @FXML
    TableView<Item> itemTbl;

    @FXML
    TableColumn itemIdCol;
    @FXML
    TableColumn itemNameCol;
    @FXML
    TableColumn itemQtyCol;
    @FXML
    TableColumn itemPriceCol;

    // The table's data
    ObservableList<Item> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the table data
        itemIdCol.setCellValueFactory(
                new PropertyValueFactory<Item, Long>("id")
        );
        itemNameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("name")
        );
        itemQtyCol.setCellValueFactory(
                new PropertyValueFactory<Item, Integer>("qty")
        );
        itemPriceCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("price")
        );

        data = FXCollections.observableArrayList();
        itemTbl.setItems(data);
    }

    static long nextId = 1;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Item item = new Item();
        item.id.setValue(nextId++);
        item.name.setValue("Item Number " + item.id.getValue());
        item.qty.setValue(10);
        Float price = new Float(5.00 + (float) item.id.getValue());
        item.price.setValue(price.toString());
        data.add(item);
    }
}

