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

    public StringProperty sequence;
    public StringProperty lEmg;
    public StringProperty lCh1;
    public StringProperty lCh2;
    public StringProperty lCh3;
    public StringProperty lCh4;
    public StringProperty lCh5;
    public StringProperty lCh6;
    public StringProperty lCh7;
    public StringProperty lCh8;
    public StringProperty lCh9;
    public StringProperty lCh10;
    public StringProperty lCh11;
    public StringProperty lCh12;
    public StringProperty lCh13;
    public StringProperty lCh14;
    public StringProperty rEmg;
    public StringProperty rCh1;
    public StringProperty rCh2;
    public StringProperty rCh3;
    public StringProperty rCh4;
    public StringProperty rCh5;
    public StringProperty rCh6;
    public StringProperty rCh7;
    public StringProperty rCh8;
    public StringProperty rCh9;
    public StringProperty rCh10;
    public StringProperty rCh11;
    public StringProperty rCh12;
    public StringProperty rCh13;
    public StringProperty rCh14;
    public StringProperty alertsTriggered;

    protected int packetNum;
    protected byte[] packet;


    public EKGDataPacket(int sequence, byte[] data) {
        this.packetNum = sequence;
        this.packet = data;

        this.sequence = new SimpleStringProperty(String.valueOf(sequence));
        this.lEmg = new SimpleStringProperty(byteToString(data[9]));
        this.lCh1 = new SimpleStringProperty(byteToString(data[10]));
        this.lCh2 = new SimpleStringProperty(byteToString(data[11]));
        this.lCh3 = new SimpleStringProperty(byteToString(data[12]));
        this.lCh4 = new SimpleStringProperty(byteToString(data[13]));
        this.lCh5 = new SimpleStringProperty(byteToString(data[14]));
        this.lCh6 = new SimpleStringProperty(byteToString(data[15]));
        this.lCh7 = new SimpleStringProperty(byteToString(data[16]));
        this.lCh8 = new SimpleStringProperty(byteToString(data[17]));
        this.lCh9 = new SimpleStringProperty(byteToString(data[18]));
        this.lCh10 = new SimpleStringProperty(byteToString(data[19]));
        this.lCh11 = new SimpleStringProperty(byteToString(data[20]));
        this.lCh12 = new SimpleStringProperty(byteToString(data[21]));
        this.lCh13 = new SimpleStringProperty(byteToString(data[22]));
        this.lCh14 = new SimpleStringProperty(byteToString(data[23]));

        this.rEmg = new SimpleStringProperty(byteToString(data[24]));
        this.rCh1 = new SimpleStringProperty(byteToString(data[25]));
        this.rCh2 = new SimpleStringProperty(byteToString(data[26]));
        this.rCh3 = new SimpleStringProperty(byteToString(data[27]));
        this.rCh4 = new SimpleStringProperty(byteToString(data[28]));
        this.rCh5 = new SimpleStringProperty(byteToString(data[29]));
        this.rCh6 = new SimpleStringProperty(byteToString(data[30]));
        this.rCh7 = new SimpleStringProperty(byteToString(data[31]));
        this.rCh8 = new SimpleStringProperty(byteToString(data[32]));
        this.rCh9 = new SimpleStringProperty(byteToString(data[33]));
        this.rCh10 = new SimpleStringProperty(byteToString(data[34]));
        this.rCh11 = new SimpleStringProperty(byteToString(data[35]));
        this.rCh12 = new SimpleStringProperty(byteToString(data[36]));
        this.rCh13 = new SimpleStringProperty(byteToString(data[37]));
        this.rCh14 = new SimpleStringProperty(byteToString(data[38]));
    }

    public EKGDataPacket(String[] data) {
        this.sequence = new SimpleStringProperty(data[0]);
        this.lEmg = new SimpleStringProperty(data[1]);
        this.lCh1 = new SimpleStringProperty(data[2]);
        this.lCh2 = new SimpleStringProperty(data[3]);
        this.lCh3 = new SimpleStringProperty(data[4]);
        this.lCh4 = new SimpleStringProperty(data[5]);
        this.lCh5 = new SimpleStringProperty(data[6]);
        this.lCh6 = new SimpleStringProperty(data[7]);
        this.lCh7 = new SimpleStringProperty(data[8]);
        this.lCh8 = new SimpleStringProperty(data[9]);
        this.lCh9 = new SimpleStringProperty(data[10]);
        this.lCh10 = new SimpleStringProperty(data[11]);
        this.lCh11 = new SimpleStringProperty(data[12]);
        this.lCh12 = new SimpleStringProperty(data[13]);
        this.lCh13 = new SimpleStringProperty(data[14]);
        this.lCh14 = new SimpleStringProperty(data[15]);

        this.rEmg = new SimpleStringProperty(data[16]);
        this.rCh1 = new SimpleStringProperty(data[17]);
        this.rCh2 = new SimpleStringProperty(data[18]);
        this.rCh3 = new SimpleStringProperty(data[19]);
        this.rCh4 = new SimpleStringProperty(data[20]);
        this.rCh5 = new SimpleStringProperty(data[21]);
        this.rCh6 = new SimpleStringProperty(data[22]);
        this.rCh7 = new SimpleStringProperty(data[23]);
        this.rCh8 = new SimpleStringProperty(data[24]);
        this.rCh9 = new SimpleStringProperty(data[25]);
        this.rCh10 = new SimpleStringProperty(data[26]);
        this.rCh11 = new SimpleStringProperty(data[27]);
        this.rCh12 = new SimpleStringProperty(data[28]);
        this.rCh13 = new SimpleStringProperty(data[29]);
        this.rCh14 = new SimpleStringProperty(data[30]);
    }

    public int getPacketNum() {
        return packetNum;
    }

    public void setPacketNum(int packetNum) {
        this.packetNum = packetNum;
    }

    public byte[] getPacket() {
        return packet;
    }

    public void setPacket(byte[] packet) {
        this.packet = packet;
    }

    public String getlCh3() {
        return lCh3.get();
    }

    public void setlCh3(String lCh3) {
        this.lCh3.set(lCh3);
    }

    public StringProperty lCh3Property() {
        return lCh3;
    }

    public String getlCh4() {
        return lCh4.get();
    }

    public void setlCh4(String lCh4) {
        this.lCh4.set(lCh4);
    }

    public StringProperty lCh4Property() {
        return lCh4;
    }

    public String getlCh5() {
        return lCh5.get();
    }

    public void setlCh5(String lCh5) {
        this.lCh5.set(lCh5);
    }

    public StringProperty lCh5Property() {
        return lCh5;
    }

    public String getlCh6() {
        return lCh6.get();
    }

    public void setlCh6(String lCh6) {
        this.lCh6.set(lCh6);
    }

    public StringProperty lCh6Property() {
        return lCh6;
    }

    public String getlCh7() {
        return lCh7.get();
    }

    public void setlCh7(String lCh7) {
        this.lCh7.set(lCh7);
    }

    public StringProperty lCh7Property() {
        return lCh7;
    }

    public String getlCh8() {
        return lCh8.get();
    }

    public void setlCh8(String lCh8) {
        this.lCh8.set(lCh8);
    }

    public StringProperty lCh8Property() {
        return lCh8;
    }

    public String getlCh9() {
        return lCh9.get();
    }

    public void setlCh9(String lCh9) {
        this.lCh9.set(lCh9);
    }

    public StringProperty lCh9Property() {
        return lCh9;
    }

    public String getlCh10() {
        return lCh10.get();
    }

    public void setlCh10(String lCh10) {
        this.lCh10.set(lCh10);
    }

    public StringProperty lCh10Property() {
        return lCh10;
    }

    public String getlCh11() {
        return lCh11.get();
    }

    public void setlCh11(String lCh11) {
        this.lCh11.set(lCh11);
    }

    public StringProperty lCh11Property() {
        return lCh11;
    }

    public String getlCh12() {
        return lCh12.get();
    }

    public void setlCh12(String lCh12) {
        this.lCh12.set(lCh12);
    }

    public StringProperty lCh12Property() {
        return lCh12;
    }

    public String getlCh13() {
        return lCh13.get();
    }

    public void setlCh13(String lCh13) {
        this.lCh13.set(lCh13);
    }

    public StringProperty lCh13Property() {
        return lCh13;
    }

    public String getlCh14() {
        return lCh14.get();
    }

    public void setlCh14(String lCh14) {
        this.lCh14.set(lCh14);
    }

    public StringProperty lCh14Property() {
        return lCh14;
    }

    public String getrEmg() {
        return rEmg.get();
    }

    public void setrEmg(String rEmg) {
        this.rEmg.set(rEmg);
    }

    public StringProperty rEmgProperty() {
        return rEmg;
    }

    public String getrCh1() {
        return rCh1.get();
    }

    public void setrCh1(String rCh1) {
        this.rCh1.set(rCh1);
    }

    public StringProperty rCh1Property() {
        return rCh1;
    }

    public String getrCh2() {
        return rCh2.get();
    }

    public void setrCh2(String rCh2) {
        this.rCh2.set(rCh2);
    }

    public StringProperty rCh2Property() {
        return rCh2;
    }

    public String getrCh3() {
        return rCh3.get();
    }

    public void setrCh3(String rCh3) {
        this.rCh3.set(rCh3);
    }

    public StringProperty rCh3Property() {
        return rCh3;
    }

    public String getrCh4() {
        return rCh4.get();
    }

    public void setrCh4(String rCh4) {
        this.rCh4.set(rCh4);
    }

    public StringProperty rCh4Property() {
        return rCh4;
    }

    public String getrCh5() {
        return rCh5.get();
    }

    public void setrCh5(String rCh5) {
        this.rCh5.set(rCh5);
    }

    public StringProperty rCh5Property() {
        return rCh5;
    }

    public String getrCh6() {
        return rCh6.get();
    }

    public void setrCh6(String rCh6) {
        this.rCh6.set(rCh6);
    }

    public StringProperty rCh6Property() {
        return rCh6;
    }

    public String getrCh7() {
        return rCh7.get();
    }

    public void setrCh7(String rCh7) {
        this.rCh7.set(rCh7);
    }

    public StringProperty rCh7Property() {
        return rCh7;
    }

    public String getrCh8() {
        return rCh8.get();
    }

    public void setrCh8(String rCh8) {
        this.rCh8.set(rCh8);
    }

    public StringProperty rCh8Property() {
        return rCh8;
    }

    public String getrCh9() {
        return rCh9.get();
    }

    public void setrCh9(String rCh9) {
        this.rCh9.set(rCh9);
    }

    public StringProperty rCh9Property() {
        return rCh9;
    }

    public String getrCh10() {
        return rCh10.get();
    }

    public void setrCh10(String rCh10) {
        this.rCh10.set(rCh10);
    }

    public StringProperty rCh10Property() {
        return rCh10;
    }

    public String getrCh11() {
        return rCh11.get();
    }

    public void setrCh11(String rCh11) {
        this.rCh11.set(rCh11);
    }

    public StringProperty rCh11Property() {
        return rCh11;
    }

    public String getrCh12() {
        return rCh12.get();
    }

    public void setrCh12(String rCh12) {
        this.rCh12.set(rCh12);
    }

    public StringProperty rCh12Property() {
        return rCh12;
    }

    public String getrCh13() {
        return rCh13.get();
    }

    public void setrCh13(String rCh13) {
        this.rCh13.set(rCh13);
    }

    public StringProperty rCh13Property() {
        return rCh13;
    }

    public String getrCh14() {
        return rCh14.get();
    }

    public void setrCh14(String rCh14) {
        this.rCh14.set(rCh14);
    }

    public StringProperty rCh14Property() {
        return rCh14;
    }

    public String byteToString(byte b) {
        return String.valueOf(b & 0xFF);
    }

    public String getSequence() {
        return sequence.get();
    }

    public void setSequence(String sequence) {
        this.sequence.set(sequence);
    }

    public StringProperty sequenceProperty() {
        return sequence;
    }

    public String getlEmg() {
        return lEmg.get();
    }

    public void setlEmg(String lEmg) {
        this.lEmg.set(lEmg);
    }

    public StringProperty lEmgProperty() {
        return lEmg;
    }

    public String getlCh1() {
        return lCh1.get();
    }

    public void setlCh1(String lCh1) {
        this.lCh1.set(lCh1);
    }

    public StringProperty lCh1Property() {
        return lCh1;
    }

    public String getlCh2() {
        return lCh2.get();
    }

    public void setlCh2(String lCh2) {
        this.lCh2.set(lCh2);
    }

    public StringProperty lCh2Property() {
        return lCh2;
    }

    public String getAlertsTriggered() {
        return alertsTriggered.get();
    }

    public void setAlertsTriggered(String alertsTriggered) {
        this.alertsTriggered.set(alertsTriggered);
    }

    public StringProperty alertsTriggeredProperty() {
        return alertsTriggered;
    }

    public void setAlertTriggered(String alert) {
        String newValue = null;
        if (alertsTriggered != null) {
            newValue = alertsTriggered.getValue() + "," + alert;
        } else {
            newValue = alert;
        }
        alertsTriggered = new SimpleStringProperty(newValue);
    }
}
