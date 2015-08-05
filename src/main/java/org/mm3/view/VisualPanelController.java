package org.mm3.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.mm3.data.MM3EventGenerator;
import org.mm3.model.MM3DataPacket;
import org.mm3.util.CommonDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author CowboyJim
 *         Date: 7/14/15
 */
public class VisualPanelController implements Observer, NestedController {

    private static final String[] channel_labels = new String[]{"0.75", "1.5", "3", "4.5", "6", "7.5", "9", "10.5", "12.5", "15", "19", "24", "30", "38"};
    private static final int CHANNELS = 14;
    protected Logger LOG = LoggerFactory.getLogger(CaptureTablePanelController.class);
    protected MainController controller;

    protected boolean tabIsVisible;

    @Autowired
    private CommonDialogs commonDialogs;
    @Autowired
    private MM3EventGenerator eventGenerator;

    private XYChart.Series<Number, String> leftChannel;
    private XYChart.Series<Number, String> rightChannel;

    @FXML
    private CustomizedStackedBarChart<Number, String> barchart;
    @FXML
    private CategoryAxis yAxis;
    @FXML
    private NumberAxis xAxis;

    @FXML
    private void initialize() {
        barchart.setTitle("MM3 Channel Data");
        xAxis.setLabel("Frequency");

        yAxis.setCategories(FXCollections.observableArrayList(channel_labels));
        leftChannel = getInitialChannelSeries(-1);
        leftChannel.setName("Left Channel");
        rightChannel = getInitialChannelSeries(1);
        rightChannel.setName("Right Channel");
        barchart.setLegendVisible(false);
        barchart.getData().addAll(leftChannel, rightChannel);
    }


    private XYChart.Series getInitialChannelSeries(int multiplier) {

        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < CHANNELS; i++) {
            series.getData().add(new XYChart.Data(multiplier * 10, channel_labels[i]));
        }
        return series;
    }

    @Override
    public void setMainController(MainController controller) {
        this.controller = controller;

        ((MainAppController) controller).getTabPane().getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.intValue() == 1) {
                tabIsVisible = true;
                eventGenerator.addObserver(this);
            } else {
                tabIsVisible = false;
                eventGenerator.deleteObserver(this);
            }
            LOG.debug("visual tabIsVisible = " + tabIsVisible);

        });
    }

    @Override
    public void update(Observable o, Object mm3Packet) {
        if (tabIsVisible) {
            //int seqNum = MM3DataPacket.getSequenceNum();
            MM3DataPacket packet = (MM3DataPacket) mm3Packet;
            populateNewChannelValues(leftChannel, packet.getLeftChannelValues(), -1);
            populateNewChannelValues(rightChannel, packet.getRightChannelValues(), 1);
        }
    }

    private void populateNewChannelValues(XYChart.Series<Number, String> channel, int[] value, int multiplier) {
        int counter = 0;
        for (XYChart.Data<Number, String> dataValue : channel.getData()) {
            dataValue.setXValue(value[counter++] * multiplier);
        }
    }
}
