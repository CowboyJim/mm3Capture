package org.mm3.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created with IntelliJ IDEA.
 * User: nbt86yz
 * Date: 7/13/15
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class EKGDataPacket {

    // init = [['EMG', 0], ['0.75', 0], ['1.5', 0], ['3', 0], ['4.5', 0], ['6', 0], ['7.5', 0], ['9', 0], ['10.5', 0], ['12.5', 0], ['15', 0], ['19', 0], ['24', 0], ['30', 0], ['38', 0]].reverse();

    public StringProperty sequence;
    public StringProperty lEmg;
    public StringProperty lCh1;
    public StringProperty lCh2;

    public EKGDataPacket(byte[] data) {
        this.sequence = new SimpleStringProperty(byteToString(data[0]));
        this.lEmg = new SimpleStringProperty(byteToString(data[1]));
        this.lCh1 = new SimpleStringProperty(byteToString(data[2]));
        this.lCh2 = new SimpleStringProperty(byteToString(data[3]));
    }

    public String byteToString(byte b) {
        return String.valueOf(b & 0xFF);
    }

    public String getSequence() {
        return sequence.get();
    }

    public StringProperty sequenceProperty() {
        return sequence;
    }

    public String getlEmg() {
        return lEmg.get();
    }

    public StringProperty lEmgProperty() {
        return lEmg;
    }

    public void setlEmg(String lEmg) {
        this.lEmg.set(lEmg);
    }

    public String getlCh1() {
        return lCh1.get();
    }

    public StringProperty lCh1Property() {
        return lCh1;
    }

    public void setlCh1(String lCh1) {
        this.lCh1.set(lCh1);
    }

    public String getlCh2() {
        return lCh2.get();
    }

    public StringProperty lCh2Property() {
        return lCh2;
    }

    public void setlCh2(String lCh2) {
        this.lCh2.set(lCh2);
    }
}
